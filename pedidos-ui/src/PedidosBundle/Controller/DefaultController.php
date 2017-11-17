<?php

namespace PedidosBundle\Controller;

use JMS\Serializer\Serializer;
use PedidosBundle\Dto\BootstrapTableDto;
use PedidosBundle\Dto\ItemsByCategoriaDto;
use PedidosBundle\Dto\MenuItemDto;
use PedidosBundle\Dto\ReportePedidosDto;
use PedidosBundle\Dto\Request\CambiarEstadoDePedidoRequest;
use PedidosBundle\Dto\Request\CambiarEstadoItemDePedidoRequest;
use PedidosBundle\Dto\Request\PedidoRequestDto;
use PedidosBundle\Dto\Request\RecibirPedidoRequest;
use PedidosBundle\Dto\Request\ReportePedidosRequestDto;
use PedidosBundle\Dto\Response\PedidoDto;
use PedidosBundle\Dto\Response\ReporteResponseDto;
use PedidosBundle\Dto\SessionDeUsuarioDto;
use PedidosBundle\Dto\SugerenciaDto;
use PedidosBundle\Dto\UsuarioDto;
use PedidosBundle\Exception\PedidosException;
use PedidosBundle\Form\LoginForm;
use PedidosBundle\Form\LoginGuestForm;
use PedidosBundle\Form\PedidoItemForm;
use PedidosBundle\Form\SugerenciaForm;
use PedidosBundle\FormEntity\LoginFormEntity;
use PedidosBundle\FormEntity\LoginGuestFormEntity;
use PedidosBundle\FormEntity\PedidoItemFormEntity;
use PedidosBundle\FormEntity\SugerenciaFormEntity;
use PedidosBundle\Service\PedidosApiHttpClient;
use PedidosBundle\Service\PedidosService;
use PedidosBundle\Util\PedidosDateUtil;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Form\FormError;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;

class DefaultController extends Controller
{
    const HARDCODED_MENU_ID = 1;
    const PEDIDO_DTO_ARRAY_SESSION_NAME = "pedidoDtoArray";

    /**
     * @Route("/", name="_index")
     */
    public function indexAction()
    {
        return $this->render('PedidosBundle:Default:index.html.twig');
    }

    /**
     * @deprecated No usar, es solo de test
     * @Route("/menu_items", name="_get_menu_items")
     */
    public function getMenuItemsAction()
    {
        /** @var PedidosApiHttpClient $client */
        $client = $this->container->get(PedidosApiHttpClient::SERVICE_NAME);

        $result = $client->findMenu();
        return new Response($result->getNombre() . ", status:" . $result->getStatus());
    }

    /**
     * @Route("/menu", name="_get_menu")
     */
    public function getMenuAction()
    {
        $pedidosService = $this->getPedidosService();

        /** @var ItemsByCategoriaDto $itemsByCategoria */
        $itemsByCategoria = $pedidosService->findMenuItemsByCategoria();

        $sugerencias = $pedidosService->findSugerencias();

        $this->getSugerenciaItem($sugerencias, $itemsByCategoria);

        return $this->render("@Pedidos/default/menu.html.twig", array("itemsByCategoriaDto" => $itemsByCategoria));
    }

    /**
     * @Route("/pedido", name="_pedido")
     */
    public function pedidoAction(Request $request)
    {
        if (!$this->getUsuario($request)->puedeCrearPedido()) {
            return $this->sinPermisosResponse();
        }

        $pedidosService = $this->getPedidosService();

        /** @var ItemsByCategoriaDto $itemsByCategoria */
        $itemsByCategoria = $pedidosService->findMenuItemsByCategoria();

        $sugerencias = $pedidosService->findSugerencias();

        $this->getSugerenciaItem($sugerencias, $itemsByCategoria);

        return $this->render("@Pedidos/default/menu.html.twig", array("itemsByCategoriaDto" => $itemsByCategoria, "modoPedir" => true));
    }

    /**
     * @Route("/pedido_item", name="_pedido_item")
     * @param Request $request
     * @return Response
     */
    public function agregarItemDePedidoAction(Request $request)
    {
        if (!$this->getUsuario($request)->puedeCrearPedido()) {
            return $this->sinPermisosResponse();
        }

        $formEntity = new PedidoItemFormEntity();

        if ($request->get("pedido_item_id")) {
            $formEntity->setId($request->get("pedido_item_id"));
        }

        $form = $this->createForm(PedidoItemForm::class, $formEntity);
        $form->handleRequest($request);

        $response = new Response();

        if ($form->isSubmitted()) {
            if ($form->isValid()) {
                $pedidoRequestDto = $this->getPedidoRequestDto($request);
                $pedidoRequestDto->addItem($formEntity->toItemDePedidoRequestDto());
                $this->savePedidoRequestDto($request, $pedidoRequestDto);

                $form = $this->createForm(PedidoItemForm::class, new PedidoItemFormEntity());
            } else {
                $response = new Response("", Response::HTTP_BAD_REQUEST);
            }
        }

        return $this->render(
            "PedidosBundle:default:pedido_item_form.html.twig",
            array("form" => $form->createView()),
            $response
        );
    }

    /**
     * @Route("/pedido_preview", name="_pedido_preview")
     * @param Request $request
     * @return Response
     */
    public function pedidoPreviewAction(Request $request)
    {
        if (!$this->getUsuario($request)->puedeCrearPedido()) {
            return $this->sinPermisosResponse();
        }

        /** @var ItemsByCategoriaDto $itemsByCategoria */
        $itemsByCategoria = $this->getPedidosService()->findMenuItemsByCategoria();

        $sugerencias = $this->getPedidosService()->findSugerencias();

        $this->getSugerenciaItem($sugerencias, $itemsByCategoria);

        return $this->render(
            "PedidosBundle:default:pedido.html.twig",
            array("itemsByCategoriaDto" => $itemsByCategoria, "pedidoRequestDto" => $this->getPedidoRequestDto($request))
        );
    }

    /**
     * @deprecated Solo test TODO Borrar este action más adelante!
     * @Route("/pedido_limpiar", name="_pedido_limpiar")
     * @param Request $request
     * @return RedirectResponse
     */
    public function pedidoLimpiarAction(Request $request)
    {
        if (!$this->getUsuario($request)->puedeCrearPedido()) {
            return $this->sinPermisosResponse();
        }

        $this->generarNuevoPedidoRequestDto($request);
        return $this->redirectToRoute("_get_menu");
    }

    /**
     * @Route("/pedido_confirmar", name="_pedido_confirmar")
     * @param Request $request
     * @return RedirectResponse
     */
    public function confirmarPedidoAction(Request $request)
    {
        if (!$this->getUsuario($request)->puedeCrearPedido()) {
            return $this->sinPermisosResponse();
        }

        $pedidoRequestDto = $this->getPedidoRequestDto($request);

        if (!$pedidoRequestDto->isEmpty()) {
            $pedidoRequestDto->setComentario($request->get("comentario"));
            $pedidoRequestDto->setDestino($request->get("destino"));
            try {
                $this->getPedidosService()->confirmarPedido($pedidoRequestDto);
                $this->generarNuevoPedidoRequestDto($request);
            } catch (PedidosException $e) {
                return new Response($e->getMessage(), Response::HTTP_I_AM_A_TEAPOT);
            }
        }
        // TODO agregar algo para mostrar confirmacion de pedido
        return $this->redirectToRoute("_get_menu");
    }


    /**
     * @Route("/pedido_listar", name="_pedido_listar")
     * @param Request $request
     * @return Response
     */
    public function pedidoListarAction(Request $request) {
        if (!$this->getUsuario($request)->puedeListarPedidos()) {
            return $this->sinPermisosResponse();
        }

        $pedidos = $this->setPedidosDtoArrayInSession($request);

        return $this->render(
            "PedidosBundle:default:listar_pedidos.html.twig",
            array("pedidos" => $pedidos));
    }


    /**
     * @Route("/login", name="_login")
     */
    public function loginAction(Request $request) {
        return $this->render("PedidosBundle:default:login.html.twig");
    }

    /**
     * @Route("/logout", name="_logout")
     */
    public function logoutAction(Request $request) {
        $this->getPedidosService()->logout();

        $request->getSession()->remove(UsuarioDto::SESSION_NAME);
        $request->getSession()->remove(self::PEDIDO_DTO_ARRAY_SESSION_NAME);
        $request->getSession()->invalidate(0);

        return $this->render("PedidosBundle:default:login.html.twig");
    }

    /**
     * @Route("/login_form", name="_login_form")
     * @param Request $request
     * @return Response
     */
    public function loginFormAction(Request $request) {
        $formEntity = new LoginFormEntity();

        $form = $this->createForm(LoginForm::class, $formEntity);
        $form->handleRequest($request);

        $response = new Response();

        if ($form->isSubmitted()) {
            if ($form->isValid()) {
                try {

                    /** @var SessionDeUsuarioDto $sessionDeUsuarioDto */
                    $sessionDeUsuarioDto = $this->getPedidosService()->login($formEntity->getEmail(), $formEntity->getPassword());
                    $this->setUserInSession($request, $sessionDeUsuarioDto);
                    $form = $this->createForm(LoginForm::class, new LoginFormEntity());
                } catch(PedidosException $e) {
                    $response = new Response("", Response::HTTP_I_AM_A_TEAPOT);
                    $error = $e->getMessage();
                    if (empty($error) || $error == "Invalid user credentials") {
                        $error = "Usuario o contraseña incorrectos.";
                    }
                    $form->get('password')->addError(new FormError($error));
                }
            } else {
                $response = new Response("", Response::HTTP_BAD_REQUEST);
            }
        }

        return $this->render(
            "PedidosBundle:default:login_form.html.twig", array("form" => $form->createView()), $response
        );
    }

    /**
     * @Route("/login_guest_form", name="_login_guest_form")
     * @param Request $request
     * @return Response
     */
    public function loginGuestFormAction(Request $request) {
        $formEntity = new LoginGuestFormEntity();

        $form = $this->createForm(LoginGuestForm::class, $formEntity);
        $form->handleRequest($request);

        $response = new Response();

        if ($form->isSubmitted()) {
            if ($form->isValid()) {
                try {
                    /** @var SessionDeUsuarioDto $sessionDeUsuarioDto */
                    $sessionDeUsuarioDto = $this->getPedidosService()->loginGuest($formEntity->getNickname());
                    $this->setUserInSession($request, $sessionDeUsuarioDto);
                    $form = $this->createForm(LoginGuestForm::class, new LoginGuestFormEntity());
                } catch(PedidosException $e) {
                    $response = new Response("", Response::HTTP_BAD_REQUEST);
                    $form->get('nickname')->addError(new FormError('Ya existe un invitado con ese nickname'));
                }
            } else {
                $response = new Response("", Response::HTTP_BAD_REQUEST);
            }
        }

        return $this->render(
            "PedidosBundle:default:login_guest_form.html.twig", array("form" => $form->createView()), $response
        );
    }

    /**
     * @Route("/reportes", name="_reportes")
     * @param Request $request
     * @return Response
     */
    public function reporteGenerarAction(Request $request) {
        if (!$this->getUsuario($request)->puedeGenerarReporte()) {
            return $this->sinPermisosResponse();
        }

        $estadoPedidos = $this->getPedidosService()->getEstadoPedidos();

        return $this->render(
            "PedidosBundle:default:generar_reporte.html.twig",array('estadoPedidos' =>$estadoPedidos));
    }

    /**
    * @Route("/reporte_list", name="_reporte_list")
    * @param $request
    * @return Response
    */
    public function reporteListAction(Request $request)
    {
        if (!$this->getUsuario($request)->puedeGenerarReporte()) {
            return $this->sinPermisosResponse();
        }

        $this->get('logger')->debug('reporteListAction');
        $from = $request->get('from');
        $to = $request->get('to');
        $estado = $request->get('estado');

        // Cuando no se envia ninguna fecha, no devulve nada.
        if($from == '#date-from'){
            return new Response(Response::HTTP_OK);
        }

        $fechaDesde = PedidosDateUtil::toPedidosApiFormat($from);
        $fechaHasta = PedidosDateUtil::toPedidosApiFormat($to);

        $reportePedidosRequest = new ReportePedidosRequestDto($fechaDesde,$fechaHasta,$estado);

        $reportePedidos = $this->getPedidosService()->generarReportePedidos($reportePedidosRequest);

        $bootstrapTable = new BootstrapTableDto(
            $reportePedidos->getItems(),
            sizeof($reportePedidos->getItems()));


        /** @var Serializer $serializer */
        $serializer = $this->get("serializer");
        $json = $serializer->serialize($bootstrapTable, 'json');
        $this->get('logger')->debug("Reporte: " . json_encode($json, JSON_PRETTY_PRINT));

        return new Response($json, Response::HTTP_OK, array("Content-Type: application/json"));
    }

    /**
     * @Route("/pedido_cambiar_estado_form", name="_pedido_cambiar_estado_form")
     * @param $request
     * @return Response
     */
    public function pedidoCambiarEstadoFormAction(Request $request) {
        $cambiarEstadoPedidoRequest = $this->crearCambiarEstadoPedidoRequestFromRequest($request);
        try {
            $this->getPedidosService()->cambiarEstadoPedido($cambiarEstadoPedidoRequest);
            $this->setPedidosDtoArrayInSession($request);
        } catch (PedidosException $e) {
            return new Response($e->getMessage(), Response::HTTP_I_AM_A_TEAPOT);
        }

        return new Response();
    }

    /**
     * Para crear la request levanta a mano los campos posteados
     * @param Request $request
     * @return CambiarEstadoDePedidoRequest
     */
    private function crearCambiarEstadoPedidoRequestFromRequest(Request $request) {
        $estadoPedido = $request->request->get("pedido_estado_form_pedido_estado");
        $abonado = $request->request->get("pedido_estado_form_abonado") == "on" ? true: false;
        $destino = $request->request->get("pedido_estado_form_destino");
        $comentario = $request->request->get("pedido_estado_form_comentario");
        $destino = empty($destino) ? null : $destino;
        $comentario = empty($comentario) ? null : $comentario;

        $cambiarEstadoPedidoRequest = new CambiarEstadoDePedidoRequest();
        $cambiarEstadoPedidoRequest->setIdPedido($request->get("pedido_id"));
        $cambiarEstadoPedidoRequest->setComentario($comentario);
        $cambiarEstadoPedidoRequest->setDestino($destino);
        $cambiarEstadoPedidoRequest->setEstadoPedido($estadoPedido);
        $cambiarEstadoPedidoRequest->setAbonado($abonado);

        /** @var array $estadoItemPedidoArray */
        $estadoItemPedidoArray = $request->request->get("pedido_estado_form_pedido_item_estado");

        /** @var array $comentarioItemPedidoArray */
        $comentarioItemPedidoArray = $request->request->get("pedido_estado_form_pedido_item_comentario");

        /**
         * @var int $pedidoItemId
         * @var string $pedidoItemEstado
         */
        foreach ($estadoItemPedidoArray as $pedidoItemId => $pedidoItemEstado) {
            $cambiarEstadoItemPedidoRequest = new CambiarEstadoItemDePedidoRequest();
            $cambiarEstadoItemPedidoRequest->setIdItemDePedido($pedidoItemId);
            $cambiarEstadoItemPedidoRequest->setEstadoItemDePedido($pedidoItemEstado);
            $cambiarEstadoItemPedidoRequest->setComentario($comentarioItemPedidoArray[$pedidoItemId]);

            $cambiarEstadoPedidoRequest->addCambiarEstadoItem($cambiarEstadoItemPedidoRequest);
        }

        return $cambiarEstadoPedidoRequest;
    }

    /**
     * @param Request $request
     * @return PedidoRequestDto
     */
    private function getPedidoRequestDto(Request $request) {
        if ($request->getSession()->has(PedidoRequestDto::class)) {
            return $request->getSession()->get(PedidoRequestDto::class);
        } else {
            return $this->generarNuevoPedidoRequestDto($request);
        }
    }

    private function savePedidoRequestDto(Request $request, PedidoRequestDto $pedidoRequestDto) {
        $request->getSession()->set(PedidoRequestDto::class, $pedidoRequestDto);
    }

    private function getPedidosService() {
        /** @var PedidosService $pedidosService */
        $pedidosService = $this->container->get(PedidosService::SERVICE_NAME);

        return $pedidosService;
    }

    private function generarNuevoPedidoRequestDto(Request $request) {
        $pedidoRequestDto = new PedidoRequestDto();

        // TODO DESHARCODEAR!!
        $pedidoRequestDto->setIdMenu(self::HARDCODED_MENU_ID);
        $this->savePedidoRequestDto($request, $pedidoRequestDto);

        return $pedidoRequestDto;
    }

    /**
     * @Route("/generar_sugerencia", name="_generar_sugerencia")
     * @param Request $request
     * @return Response
     */
    public function generarSugerenciaAction(Request $request)
    {
        if (!$this->getUsuario($request)->puedeCrearSugerencia()) {
            return $this->sinPermisosResponse();
        }

        $formEntity = new SugerenciaFormEntity();

        $form = $this->createForm(SugerenciaForm::class, $formEntity);
        $form->handleRequest($request);

        $response = new Response();

        if ($form->isSubmitted()) {
            if ($form->isValid()) {
                $sugerenciaRequestDto = $formEntity->toSugerenciaRequestDto();
                $this->getPedidosService()->crearSugerencia($sugerenciaRequestDto);

                $this->addFlash('notice', 'Sugerencia creada exitosamente');
                return $this->redirectToRoute('_get_menu');

            }
        }

        return $this->render(
            "PedidosBundle:default:sugerencia_form.html.twig",
            array("form" => $form->createView()),
            $response
        );
    }

    /**
     * @Route("/cambiar_estado_pedido", name="_cambiar_estado_pedido")
     * @param Request $request
     * @return Response
     */
    public function cambiarEstadoPedidoAction(Request $request) {
        if (!$this->getUsuario($request)->puedeCambiarEstadoDePedido()) {
            return $this->sinPermisosResponse();
        }

        $pedidoId = $request->get("pedido_id");

        $pedidoDto = $this->getPedidoById($request, $pedidoId);
        $estadoPedidos = $this->getPedidosService()->getEstadoPedidos();
        $estadoItemPedidos = $this->getPedidosService()->getEstadoItemPedidos();

        if (!$pedidoDto) {
            return new Response("No existe el pedido", Response::HTTP_NOT_FOUND);
        }

        return $this->render("PedidosBundle:default:cambiar_estado_pedido.html.twig",
            array("pedidoDto" => $pedidoDto,'estadoPedidos' => $estadoPedidos,'estadoItemPedidos' => $estadoItemPedidos));
    }

    /**
     * @param $sugerencias
     * @param $itemsByCategoria
     */
    private function getSugerenciaItem($sugerencias, $itemsByCategoria)
    {
        $sugerenciaItem = array();
        /** @var SugerenciaDto $sugerencia */
        foreach ($sugerencias as $sugerencia) {
            array_push($sugerenciaItem, $sugerencia->getItemDeMenu());
        }
        $itemsByCategoria->setSugerenciaItems($sugerenciaItem);
    }

    /**
     * @param Request $request
     * @param $sessionDeUsuarioDto
     */
    private function setUserInSession(Request $request, SessionDeUsuarioDto $sessionDeUsuarioDto)
    {
        $sessionDeUsuarioDto->getUsuario()->setSessionId($sessionDeUsuarioDto->getId());
        $request->getSession()->set(UsuarioDto::SESSION_NAME, $sessionDeUsuarioDto->getUsuario());
    }

    /**
     * @param Request $request
     * @return UsuarioDto
     */
    private function getUsuario(Request $request) {

        return $request->getSession()->get(UsuarioDto::SESSION_NAME);
    }

    /**
     * @return Response
     */
    private function sinPermisosResponse() {
        return new Response("El usuario no posee permisos para realizar la operación", Response::HTTP_FORBIDDEN);
    }

    /**
     * @param Request $request
     * @param $pedidoId
     * @return PedidoDto
     */
    private function getPedidoById(Request $request, $pedidoId) {
        $pedidoDtoArray = $request->getSession()->get(self::PEDIDO_DTO_ARRAY_SESSION_NAME);

        /** @var PedidoDto $pedidoDto */
        foreach ($pedidoDtoArray as $pedidoDto) {
            if ($pedidoDto->getId() == $pedidoId) {
                return $pedidoDto;
            }
        }

        return null;
    }

    /**
     * @param Request $request
     * @return array
     */
    private function setPedidosDtoArrayInSession(Request $request) {
        $pedidos = $this->getPedidosService()->findPedidos();
        $request->getSession()->set(self::PEDIDO_DTO_ARRAY_SESSION_NAME, $pedidos);

        return $pedidos;
    }
}

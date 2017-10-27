<?php

namespace PedidosBundle\Controller;

use JMS\Serializer\Serializer;
use PedidosBundle\Dto\BootstrapTableDto;
use PedidosBundle\Dto\ItemsByCategoriaDto;
use PedidosBundle\Dto\MenuItemDto;
use PedidosBundle\Dto\Request\LoginUsuarioRegistradoRequestDto;
use PedidosBundle\Dto\Request\PedidoRequestDto;
use PedidosBundle\Dto\Response\ReporteResponseDto;
use PedidosBundle\Dto\SessionDeUsuarioDto;
use PedidosBundle\Dto\UsuarioDto;
use PedidosBundle\Exception\PedidosException;
use PedidosBundle\Form\LoginForm;
use PedidosBundle\Form\PedidoItemForm;
use PedidosBundle\Form\SugerenciaForm;
use PedidosBundle\FormEntity\LoginFormEntity;
use PedidosBundle\FormEntity\PedidoItemFormEntity;
use PedidosBundle\FormEntity\SugerenciaFormEntity;
use PedidosBundle\Service\PedidosApiHttpClient;
use PedidosBundle\Service\PedidosService;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Form\FormError;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;

class DefaultController extends Controller
{
    const HARDCODED_MENU_ID = 1;

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
        return $this->render("@Pedidos/default/menu.html.twig", array("itemsByCategoriaDto" => $itemsByCategoria));
    }

    /**
     * @Route("/pedido", name="_pedido")
     */
    public function pedidoAction()
    {
        $pedidosService = $this->getPedidosService();

        /** @var ItemsByCategoriaDto $itemsByCategoria */
        $itemsByCategoria = $pedidosService->findMenuItemsByCategoria();
        return $this->render("@Pedidos/default/menu.html.twig", array("itemsByCategoriaDto" => $itemsByCategoria, "modoPedir" => true));
    }

    /**
     * @Route("/pedido_item", name="_pedido_item")
     * @param Request $request
     * @return Response
     */
    public function agregarItemDePedidoAction(Request $request)
    {
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
        /** @var ItemsByCategoriaDto $itemsByCategoria */
        $itemsByCategoria = $this->getPedidosService()->findMenuItemsByCategoria();

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
        $pedidoRequestDto = $this->getPedidoRequestDto($request);

        if (!$pedidoRequestDto->isEmpty()) {
            $pedidoRequestDto->setComentario($request->get("comentario"));
            $this->getPedidosService()->confirmarPedido($pedidoRequestDto);
            $this->generarNuevoPedidoRequestDto($request);
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
        $pedidos = $this->getPedidosService()->findPedidos();

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
        $request->getSession()->remove(UsuarioDto::SESSION_NAME);
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
                    $request->getSession()->set(UsuarioDto::SESSION_NAME, $sessionDeUsuarioDto->getUsuario());
                    $form = $this->createForm(LoginForm::class, new LoginFormEntity());
                } catch(PedidosException $e) {
                    $response = new Response("", Response::HTTP_BAD_REQUEST);
                    $form->get('password')->addError(new FormError('Usuario o contraseña incorrectos.'));
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
     * @Route("/reportes", name="_reportes")
     * @param Request $request
     * @return Response
     */
    public function reporteGenerarAction(Request $request) {
        return $this->render(
            "PedidosBundle:default:generar_reporte.html.twig");
    }

    /**
    * @Route("/reporte_list", name="_reporte_list")
    * @param $request
    * @return Response
    */
    public function reporteListAction(Request $request)
    {
        $this->get('logger')->debug('reporteListAction');
        $from = $request->get('from');
        $to = $request->get('to');

        $arrayReporte = array();

        /* TODO Reemplazar con el TambuServicio */
        $reporteDto1 = new ReporteResponseDto();
        $reporteDto1->setCantidad(4);
        $menuItem1 = new MenuItemDto();
        $menuItem1->setNombre("Papas con cheddar");

        $reporteDto1->setItemDeMenu($menuItem1);

        array_push($arrayReporte,$reporteDto1);

        $reporteDto2 = new ReporteResponseDto();
        $reporteDto2->setCantidad(6);
        $menuItem2 = new MenuItemDto();
        $menuItem2->setNombre("Cerveza Artesanal");
        $reporteDto2->setItemDeMenu($menuItem2);

        array_push($arrayReporte,$reporteDto2);

        $bootstrapTable = new BootstrapTableDto(
            $arrayReporte,
            sizeof($arrayReporte));


        /** @var Serializer $serializer */
        $serializer = $this->get("serializer");
        $json = $serializer->serialize($bootstrapTable, 'json');
        $this->get('logger')->debug("Reporte: " . json_encode($json, JSON_PRETTY_PRINT));

        return new Response($json, Response::HTTP_OK, array("Content-Type: application/json"));
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
        $formEntity = new SugerenciaFormEntity();

        $form = $this->createForm(SugerenciaForm::class, $formEntity);
        $form->handleRequest($request);

        $response = new Response();

        if ($form->isSubmitted()) {
            if ($form->isValid()) {

            }
        }

        return $this->render(
            "PedidosBundle:default:sugerencia_form.html.twig",
            array("form" => $form->createView()),
            $response
        );
    }
}

<?php

namespace PedidosBundle\Controller;

use PedidosBundle\Dto\ItemsByCategoriaDto;
use PedidosBundle\Dto\Request\PedidoRequestDto;
use PedidosBundle\Form\PedidoItemForm;
use PedidosBundle\FormEntity\PedidoItemFormEntity;
use PedidosBundle\Service\PedidosApiHttpClient;
use PedidosBundle\Service\PedidosService;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;

class DefaultController extends Controller
{

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
        $pedidoRequestDto = new PedidoRequestDto();

        // TODO DESHARCODEAR!!
        $pedidoRequestDto->setIdMenu(1);
        $this->savePedidoRequestDto($request, $pedidoRequestDto);
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
     * @param Request $request
     * @return PedidoRequestDto
     */
    private function getPedidoRequestDto(Request $request) {
        if ($request->getSession()->has(PedidoRequestDto::class)) {
            return $request->getSession()->get(PedidoRequestDto::class);
        } else {
            $pedidoRequestDto = new PedidoRequestDto();

            // TODO DESHARCODEAR!!
            $pedidoRequestDto->setIdMenu(1);
            $request->getSession()->set(PedidoRequestDto::class, $pedidoRequestDto);
            return $pedidoRequestDto;
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
}

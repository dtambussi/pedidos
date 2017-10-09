<?php

namespace PedidosBundle\Controller;

use PedidosBundle\Dto\ItemsByCategoriaDto;
use PedidosBundle\Dto\PedidoRequestDto;
use PedidosBundle\Form\PedidoItemForm;
use PedidosBundle\FormEntity\PedidoItemFormEntity;
use PedidosBundle\Service\PedidosApiHttpClient;
use PedidosBundle\Service\PedidosService;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
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
        /** @var PedidosService $pedidosService */
        $pedidosService = $this->container->get(PedidosService::SERVICE_NAME);

        /** @var ItemsByCategoriaDto $itemsByCategoria */
        $itemsByCategoria = $pedidosService->findMenuItemsByCategoria();
        return $this->render("@Pedidos/default/menu.html.twig", array("itemsByCategoriaDto" => $itemsByCategoria));
    }

    /**
     * @Route("/pedido_item", name="_pedido_item")
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

    public function enviarPedido()
    {

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
}

<?php

namespace PedidosBundle\Controller;

use PedidosBundle\Dto\ItemsByCategoriaDto;
use PedidosBundle\Service\PedidosApiHttpClient;
use PedidosBundle\Service\PedidosService;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
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
}

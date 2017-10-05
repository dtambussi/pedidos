<?php

namespace PedidosBundle\Controller;

use PedidosBundle\Service\PedidosApiHttpClient;
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
     * @Route("/menu", name="_get_menu")
     */
    public function getMenuAction()
    {
        /** @var PedidosApiHttpClient $client */
        $client = $this->container->get(PedidosApiHttpClient::SERVICE_NAME);

        $result = $client->getMenu();
        return new Response($result->getNombre() . ", status:" . $result->getStatus());
    }
}

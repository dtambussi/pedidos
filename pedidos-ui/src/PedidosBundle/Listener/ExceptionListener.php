<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 16/11/2017
 * Time: 12:19
 */

namespace PedidosBundle\Listener;


use PedidosBundle\Exception\PedidosException;
use Psr\Log\LoggerInterface;
use Symfony\Component\HttpFoundation\Session\Session;
use Symfony\Component\HttpKernel\Event\GetResponseForExceptionEvent;

class ExceptionListener
{

    /**
     * @var LoggerInterface
     */
    private $logger;

    /**
     * @var Session
     */
    private $session;

    /**
     * ExceptionListener constructor.
     * @param $logger
     * @param $session
     */
    public function __construct($logger, $session)
    {
        $this->logger = $logger;
        $this->session = $session;
    }


    public function onKernelException(GetResponseForExceptionEvent $event)
    {
        // You get the exception object from the received event
        $exception = $event->getException();

        if ($exception instanceof PedidosException) {
            $this->logger->info("!!!!!!!! PEDIDOS EXCEPTION !!!!!!!! ");

            $this->session->getFlashBag()->add('notice', $exception->getMessage());
            return;
        }
    }
}
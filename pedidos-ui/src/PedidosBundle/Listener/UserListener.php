<?php

namespace PedidosBundle\Listener;


use PedidosBundle\Dto\UsuarioDto;
use Psr\Log\LoggerInterface;
use Symfony\Component\ExpressionLanguage\Token;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpKernel\Event\GetResponseEvent;
use Symfony\Component\Routing\Router;
use Symfony\Component\Security\Core\Authentication\Token\Storage\TokenStorage;
use Symfony\Component\Security\Core\Exception\SessionUnavailableException;

class UserListener
{
    /** @var  LoggerInterface */
    private $logger;

    /** @var  Router */
    private $router;

    /**
     * UsuarioRequisitosListener constructor.
     * @param LoggerInterface $logger
     * @param Router $router
     */
    public function __construct(LoggerInterface $logger, Router $router)
    {
        $this->logger = $logger;
        $this->router = $router;
    }

    /**
     *
     * @param GetResponseEvent $event
     */
    public function onKernelRequest(GetResponseEvent $event)
    {
        $this->logger->info('************** ENTRO UserListener ************** ');

        $requestUri = $event->getRequest()->getRequestUri();

        if (!strpos($requestUri, "login") && !strpos($requestUri, "logout")) {
            if (!$event->getRequest()->getSession()->has(UsuarioDto::SESSION_NAME)) {
                $event->setResponse(new RedirectResponse($this->router->generate("_login")));
                return;
            }
        }
    }
}

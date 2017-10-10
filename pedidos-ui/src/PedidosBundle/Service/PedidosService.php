<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/5/17
 * Time: 5:30 PM
 */

namespace PedidosBundle\Service;


use PedidosBundle\Dto\ItemsByCategoriaDto;
use PedidosBundle\Dto\Request\PedidoRequestDto;
use Psr\Log\LoggerInterface;

class PedidosService
{
    const SERVICE_NAME = "pedidos_service";

    /**
     * @var LoggerInterface
     */
    private $loger;

    /**
     * @var PedidosApiHttpClient
     */
    private $pedidosApiHttpClient;

    /**
     * PedidosService constructor.
     * @param LoggerInterface $loger
     * @param PedidosApiHttpClient $pedidosApiHttpClient
     */
    public function __construct(LoggerInterface $loger, PedidosApiHttpClient $pedidosApiHttpClient)
    {
        $this->loger = $loger;
        $this->pedidosApiHttpClient = $pedidosApiHttpClient;
    }

    public function findMenuItemsByCategoria() {
        $menuDto = $this->pedidosApiHttpClient->findMenu();
        $result = new ItemsByCategoriaDto($menuDto);

        return $result;
    }

    public function confirmarPedido(PedidoRequestDto $pedidoRequestDto) {
        $this->pedidosApiHttpClient->confirmarPedido($pedidoRequestDto);
    }

    public function findPedidos() {
        return $this->pedidosApiHttpClient->findPedidos();
    }
}
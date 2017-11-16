<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/5/17
 * Time: 5:30 PM
 */

namespace PedidosBundle\Service;


use PedidosBundle\Dto\ItemsByCategoriaDto;
use PedidosBundle\Dto\Request\CambiarEstadoDePedidoRequest;
use PedidosBundle\Dto\Request\PedidoRequestDto;
use PedidosBundle\Dto\Request\RecibirPedidoRequest;
use PedidosBundle\Dto\Request\ReportePedidosRequestDto;
use PedidosBundle\Dto\Request\SugerenciaRequestDto;
use PedidosBundle\Dto\Response\PedidoDto;
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
        $result = new ItemsByCategoriaDto($menuDto->getItems());

        return $result;
    }

    public function findSugerencias() {
        return $this->pedidosApiHttpClient->findSugerencias();
    }

    public function confirmarPedido(PedidoRequestDto $pedidoRequestDto) {
        $this->pedidosApiHttpClient->confirmarPedido($pedidoRequestDto);
    }

    public function findPedidos() {
        $pedidoDtoArray = $this->pedidosApiHttpClient->findPedidos();

        // Esto es para llenar el campo con los items discriminados por categoría
        /** @var PedidoDto $pedidoDto */
        foreach ($pedidoDtoArray as $pedidoDto) {
            $itemsByCategoriaDto = new ItemsByCategoriaDto($pedidoDto->getItems());
            $pedidoDto->setItemsByCategoriaDto($itemsByCategoriaDto);
        }

        return $pedidoDtoArray;
    }

    public function login($email, $password) {
        return $this->pedidosApiHttpClient->doLogin($email, $password);
    }

    public function loginGuest($nickname) {
        return $this->pedidosApiHttpClient->doLoginGuest($nickname);
    }

    public function crearSugerencia(SugerenciaRequestDto $sugerenciaRequestDto) {
        $this->pedidosApiHttpClient->generarSugerencia($sugerenciaRequestDto);
    }

    public function generarReportePedidos(ReportePedidosRequestDto $reportePedidosRequestDto){
        return $this->pedidosApiHttpClient->generarReportePedidos($reportePedidosRequestDto);
    }

    /**
     * @param CambiarEstadoDePedidoRequest $cambiarEstadoPedidoRequest
     */
    public function cambiarEstadoPedido(CambiarEstadoDePedidoRequest $cambiarEstadoPedidoRequest)
    {
        $this->pedidosApiHttpClient->cambiarEstadoDePedido($cambiarEstadoPedidoRequest);
    }

    /**
     * @param RecibirPedidoRequest $recibirPedidoRequest
     */
    public function recibirPedido(RecibirPedidoRequest $recibirPedidoRequest) {
        $this->pedidosApiHttpClient->recibirPedido($recibirPedidoRequest);
    }

    public function logout() {
        $this->pedidosApiHttpClient->logout();
    }
}
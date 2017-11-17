<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/9/17
 * Time: 12:11 PM
 */

namespace PedidosBundle\Dto\Request;



class ReportePedidosRequestDto
{



    /**
     * @var string
     */
    private $fechaDesde;

    /**
     * @var string
     */
    private $fechaHasta;

    /**
     * @var string
     */
    private $estadoPedido;

    /**
     * ReportePedidosRequestDto constructor.
     * @param string $fechaDesde
     * @param string $fechaHasta
     */
    public function __construct($fechaDesde, $fechaHasta,$estado)
    {
        $this->fechaDesde = $fechaDesde;
        $this->fechaHasta = $fechaHasta;
        $this->estadoPedido = empty($estado)?null:$estado;
    }

    /**
     * @return string
     */
    public function getFechaDesde()
    {
        return $this->fechaDesde;
    }

    /**
     * @param string $fechaDesde
     */
    public function setFechaDesde($fechaDesde)
    {
        $this->fechaDesde = $fechaDesde;
    }

    /**
     * @return string
     */
    public function getFechaHasta()
    {
        return $this->fechaHasta;
    }

    /**
     * @param string $fechaHasta
     */
    public function setFechaHasta($fechaHasta)
    {
        $this->fechaHasta = $fechaHasta;
    }

    /**
     * @return string
     */
    public function getEstadoPedido()
    {
        return $this->estadoPedido;
    }

    /**
     * @param string $estadoPedido
     */
    public function setEstadoPedido($estadoPedido)
    {
        $this->estadoPedido = $estadoPedido;
    }


}
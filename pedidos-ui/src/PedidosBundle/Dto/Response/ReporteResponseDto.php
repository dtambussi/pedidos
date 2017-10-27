<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/10/17
 * Time: 11:50 AM
 */

namespace PedidosBundle\Dto\Response;
use JMS\Serializer\Annotation\Type;


use PedidosBundle\Dto\MenuItemDto;

class ReporteResponseDto
{

    /**
     * @var MenuItemDto
     * @Type("PedidosBundle\Dto\MenuItemDto")
     */
    public $itemDeMenu; //ItemDeMenu

    /**
     * @var int
     * @Type("int")
     */
    public $cantidad;

    /**
     * @return MenuItemDto
     */
    public function getItemDeMenu()
    {
        return $this->itemDeMenu;
    }

    /**
     * @param MenuItemDto $itemDeMenu
     */
    public function setItemDeMenu($itemDeMenu)
    {
        $this->itemDeMenu = $itemDeMenu;
    }

    /**
     * @return int
     */
    public function getCantidad()
    {
        return $this->cantidad;
    }

    /**
     * @param int $cantidad
     */
    public function setCantidad($cantidad)
    {
        $this->cantidad = $cantidad;
    }


}
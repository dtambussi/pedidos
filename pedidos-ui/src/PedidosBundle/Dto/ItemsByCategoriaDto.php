<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/5/17
 * Time: 5:33 PM
 */

namespace PedidosBundle\Dto;


use PedidosBundle\Dto\Response\PedidoDto;
use PedidosBundle\Dto\Response\PedidoItem;

class ItemsByCategoriaDto
{
    private $platoPrincipalItems;
    private $entradaItems;
    private $bebidaItems;
    private $cafeteriaItems;
    private $postreItems;

    /**
     * ItemByCategoriaDto constructor.
     * @param array<MenuItemDto|PedidoItem> $items
     */
    public function __construct(array $items)
    {
        $this->platoPrincipalItems = array_filter($items, array($this, "isPlatoPrincipal"));
        $this->entradaItems = array_filter($items, array($this, "isEntrada"));
        $this->bebidaItems = array_filter($items, array($this, "isBebida"));
        $this->postreItems = array_filter($items, array($this, "isPostre"));
        $this->cafeteriaItems = array_filter($items, array($this, "isCafeteria"));
    }

    private function isPlatoPrincipal($itemDto) {
        if ($itemDto instanceof PedidoItem) {
            $itemDto = $itemDto->getItemDeMenu();
        }
        return $itemDto->getCategoria() == CategoriaMenuItemType::PLATOS_PRINCIPALES;
    }

    private function isBebida($itemDto) {
        if ($itemDto instanceof PedidoItem) {
            $itemDto = $itemDto->getItemDeMenu();
        }
        return $itemDto->getCategoria() == CategoriaMenuItemType::BEBIDAS;
    }

    private function isPostre($itemDto) {
        if ($itemDto instanceof PedidoItem) {
            $itemDto = $itemDto->getItemDeMenu();
        }
        return $itemDto->getCategoria() == CategoriaMenuItemType::POSTRES;
    }

    private function isCafeteria($itemDto) {
        if ($itemDto instanceof PedidoItem) {
            $itemDto = $itemDto->getItemDeMenu();
        }
        return $itemDto->getCategoria() == CategoriaMenuItemType::CAFETERIA;
    }

    private function isEntrada($itemDto) {
        if ($itemDto instanceof PedidoItem) {
            $itemDto = $itemDto->getItemDeMenu();
        }
        return $itemDto->getCategoria() == CategoriaMenuItemType::ENTRADAS;
    }

    public function getItemsByCategoria($categoria) {

    }

    /**
     * @return array
     */
    public function getPlatoPrincipalItems()
    {
        return $this->platoPrincipalItems;
    }

    /**
     * @return array
     */
    public function getEntradaItems()
    {
        return $this->entradaItems;
    }

    /**
     * @return array
     */
    public function getBebidaItems()
    {
        return $this->bebidaItems;
    }

    /**
     * @return array
     */
    public function getCafeteriaItems()
    {
        return $this->cafeteriaItems;
    }

    /**
     * @return array
     */
    public function getPostreItems()
    {
        return $this->postreItems;
    }
}
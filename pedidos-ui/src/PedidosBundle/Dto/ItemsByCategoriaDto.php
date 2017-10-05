<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/5/17
 * Time: 5:33 PM
 */

namespace PedidosBundle\Dto;


class ItemsByCategoriaDto
{
    private $platoPrincipalItems;
    private $entradaItems;
    private $bebidaItems;
    private $cafeteriaItems;
    private $postreItems;

    /**
     * ItemByCategoriaDto constructor.
     * @param MenuDto $menuDto
     */
    public function __construct(MenuDto $menuDto)
    {
        $this->platoPrincipalItems = array_filter($menuDto->getItems(), array($this, "isPlatoPrincipal"));
        $this->entradaItems = array_filter($menuDto->getItems(), array($this, "isEntrada"));
        $this->bebidaItems = array_filter($menuDto->getItems(), array($this, "isBebida"));
        $this->postreItems = array_filter($menuDto->getItems(), array($this, "isPostre"));
        $this->cafeteriaItems = array_filter($menuDto->getItems(), array($this, "isCafeteria"));
    }

    private function isPlatoPrincipal(MenuItemDto $itemDto) {
        return $itemDto->getCategoria() == CategoriaMenuItemType::PLATOS_PRINCIPALES;
    }

    private function isBebida(MenuItemDto $itemDto) {
        return $itemDto->getCategoria() == CategoriaMenuItemType::BEBIDAS;
    }

    private function isPostre(MenuItemDto $itemDto) {
        return $itemDto->getCategoria() == CategoriaMenuItemType::POSTRES;
    }

    private function isCafeteria(MenuItemDto $itemDto) {
        return $itemDto->getCategoria() == CategoriaMenuItemType::CAFETERIA;
    }

    private function isEntrada(MenuItemDto $itemDto) {
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
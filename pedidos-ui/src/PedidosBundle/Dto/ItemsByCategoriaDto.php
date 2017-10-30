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
    private $sugerenciaItems;

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
        $this->sugerenciaItems = array_filter($items, array($this, "isSugerencia"));
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

    private function isSugerencia($itemDto) {
        if ($itemDto instanceof PedidoItem) {
            $itemDto = $itemDto->getItemDeMenu();
        }
        return $itemDto->getCategoria() == CategoriaMenuItemType::SUGERENCIA;
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

    /**
     * @return mixed
     */
    public function getSugerenciaItems()
    {
        return $this->sugerenciaItems;
    }

    /**
     * @param array $platoPrincipalItems
     */
    public function setPlatoPrincipalItems($platoPrincipalItems)
    {
        $this->platoPrincipalItems = $platoPrincipalItems;
    }

    /**
     * @param array $entradaItems
     */
    public function setEntradaItems($entradaItems)
    {
        $this->entradaItems = $entradaItems;
    }

    /**
     * @param array $bebidaItems
     */
    public function setBebidaItems($bebidaItems)
    {
        $this->bebidaItems = $bebidaItems;
    }

    /**
     * @param array $cafeteriaItems
     */
    public function setCafeteriaItems($cafeteriaItems)
    {
        $this->cafeteriaItems = $cafeteriaItems;
    }

    /**
     * @param array $postreItems
     */
    public function setPostreItems($postreItems)
    {
        $this->postreItems = $postreItems;
    }

    /**
     * @param mixed $sugerenciaItems
     */
    public function setSugerenciaItems($sugerenciaItems)
    {
        $this->sugerenciaItems = $sugerenciaItems;
    }


}
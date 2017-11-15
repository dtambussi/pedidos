<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/9/17
 * Time: 11:28 AM
 */

namespace PedidosBundle\Dto\Request;


class PedidoRequestDto
{
    /**
     * @var int
     */
    private $idMenu;

    /**
     * @var string
     */
    private $fechaUltimaModificacionMenu;

    /**
     * @var string
     */
    private $comentario;

    /**
     * @var string
     */
    private $destino;

    /**
     * @var array<ItemDePedidoRequestDto>
     */
    private $items;

    /**
     * PedidoRequestDto constructor.
     */
    public function __construct()
    {
        $this->items = array();
    }


    /**
     * @return int
     */
    public function getIdMenu()
    {
        return $this->idMenu;
    }

    /**
     * @param int $idMenu
     */
    public function setIdMenu($idMenu)
    {
        $this->idMenu = $idMenu;
    }

    /**
     * @return string
     */
    public function getFechaUltimaModificacionMenu()
    {
        return $this->fechaUltimaModificacionMenu;
    }

    /**
     * @param string $fechaUltimaModificacionMenu
     */
    public function setFechaUltimaModificacionMenu($fechaUltimaModificacionMenu)
    {
        $this->fechaUltimaModificacionMenu = $fechaUltimaModificacionMenu;
    }

    /**
     * @return string
     */
    public function getComentario()
    {
        return $this->comentario;
    }

    /**
     * @param string $comentario
     */
    public function setComentario($comentario)
    {
        $this->comentario = $comentario;
    }

    /**
     * @return string
     */
    public function getDestino()
    {
        return $this->destino;
    }

    /**
     * @param string $destino
     */
    public function setDestino($destino)
    {
        $this->destino = $destino;
    }

    /**
     * @return array
     */
    public function getItems()
    {
        return $this->items;
    }

    /**
     * @param array $items
     */
    public function setItems($items)
    {
        $this->items = $items;
    }

    public function addItem(ItemDePedidoRequestDto $itemDePedidoRequestDto) {
        $esPrimerItemDeEseTipo = true;

        /** @var ItemDePedidoRequestDto $item */
        foreach ($this->items as $item) {
            if ($item->getIdItemDeMenu() == $itemDePedidoRequestDto->getIdItemDeMenu() &&
            $item->getComentario() == $itemDePedidoRequestDto->getComentario()) {
                $item->setCantidad($item->getCantidad() + $itemDePedidoRequestDto->getCantidad());
                $esPrimerItemDeEseTipo = false;
            }
        }

        if ($esPrimerItemDeEseTipo) {
            array_push($this->items, $itemDePedidoRequestDto);
        }
    }

    public function isEmpty() {
        return empty($this->items);
    }
}
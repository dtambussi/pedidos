<?php
namespace PedidosBundle\Dto;
use JMS\Serializer\Annotation\Type;

/**
 * Created by PhpStorm.
 * User: martin
 * Date: 10/30/17
 * Time: 15:32 PM
 */
class ReportePedidosDto
{
    /**
     * @var array
     * @Type("array<PedidosBundle\Dto\ItemReportePedidosDto>")
     */
    public $items;

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



}

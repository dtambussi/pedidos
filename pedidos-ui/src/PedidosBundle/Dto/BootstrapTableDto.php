<?php

namespace PedidosBundle\Dto;

class BootstrapTableDto
{

    private $rows;
    private $total;

    public function __construct($rows, $total) {
        $this->rows = $rows;
        $this->total = $total;
    }

    /**
     * @return mixed
     */
    public function getRows()
    {
        return $this->rows;
    }

    /**
     * @param mixed $rows
     */
    public function setRows($rows)
    {
        $this->rows = $rows;
    }

    /**
     * @return the $total
     */
    public function getTotal()
    {
        return $this->total;
    }

    /**
     * @param field_type $total
     */
    public function setTotal($total)
    {
        $this->total = $total;
    }

}

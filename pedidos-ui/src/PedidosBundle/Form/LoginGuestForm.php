<?php
/**
 * Created by PhpStorm.
 * User: emprendedor
 * Date: 10/27/17
 * Time: 12:47 PM
 */

namespace PedidosBundle\Form;


use PedidosBundle\FormEntity\LoginFormEntity;
use PedidosBundle\FormEntity\LoginGuestFormEntity;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class LoginGuestForm extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options) {
        $builder->add('nickname', TextType::class, array('label' => 'Nickname'));
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => LoginGuestFormEntity::class,
        ));
    }
}
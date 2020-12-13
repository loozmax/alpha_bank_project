<?php

    require_once 'connect.php';

    $title = $_POST['title'];
    $nn = $_POST['id'];

    $id = explode(',', $nn)[0];
    $datetime = explode(',', $nn)[1];

    $data = [
        'message' => $title,
        'id' => $id
    ];
    if (abs(date('d-m-Y') - $datetime) < 1) {
        $sql = ("UPDATE `message` SET `message` = :message WHERE `message`.`id` = :id");
        $connect->prepare($sql)->execute($data);
        header('Location: /');
    } else {
        header('Location: /');
    }



?>

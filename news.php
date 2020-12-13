<?php

session_start();
require_once 'vendor/connect.php';

$id = $_SESSION['user']['id'];
$my_msg = $connect->query("SELECT message, date, message.id, login FROM message JOIN registration ON registration.id = message.id_user WHERE id_user = '$id'");
$news = $connect->query("SELECT message, date, message.id, login FROM message JOIN registration ON registration.id = message.id_user WHERE NOT id_user = '$id'");

$nn = $_GET['del'];
$low = explode(',', $nn);

if (isset($_GET['del'])) {
    $id = $low[0];
    $datetime = $low[1];
    if (abs(date('d-m-Y') - $datetime) < 1) {
        $connect->query("DELETE FROM message WHERE id = $id");
        header('Location: news.php');
    }
}

?>

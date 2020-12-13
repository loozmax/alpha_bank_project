<?php
    session_start();
    require_once 'connect.php';
    $id_user = $_SESSION['user']['id'];
    $message = $_POST['wall'];
    $date = date('d-m-Y');
    if ($message) {
        $sql = ("INSERT INTO `message` (`message`, `id`, `id_user`, `date`) VALUES (?, ?, ?, ?)");

        $send_message = $connect->prepare($sql)->execute([$message, NULL, $id_user, $date]);

        header('Location: ../profile.php');
    } else {
        header('Location: ../auth.php');
    }


    $check_message = $connect->query("SELECT `message`, `id` FROM `message` WHERE `id_user` = '$id_user'");

?>


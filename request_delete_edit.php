<?php
session_start();
require_once 'vendor/connect.php';
$nn = $_GET['del'];
$low = explode(',', $nn);

$id_user = $_SESSION['user']['id'];
$check_message = $connect->query("SELECT `message`, `id`, `date` FROM `message` WHERE `id_user` = '$id_user'");
//$datetime = mysqli_fetch_assoc($check_message);
if (isset($_GET['del'])) {
    $id = $low[0];
    $datetime = $low[1];
    if (abs(date('d-m-Y') - $datetime) < 1) {
        $sql = ("DELETE FROM message WHERE id = :id");
        $data = [
            'id'=>$id
        ];
        $connect->prepare($sql)->execute($data);
        header('Location: ../profile.php');
    } else {
        header('Location: ../profile.php');
    }
}

?>
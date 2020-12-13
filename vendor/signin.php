<?php
    session_start();
    require_once 'connect.php';
    $login = $_POST['login'];
    $password = md5($_POST['password']);

    $check_user = $connect->query("SELECT * FROM `registration` WHERE (`login` = '$login' OR `email` = '$login') AND `password` = '$password'");
    $row = $check_user->rowCount();
    if($row > 0) {
        $user = $check_user->fetch(PDO::FETCH_ASSOC);

        $_SESSION['user'] = [
            'id' => $user['id'],
            'login' => $user['login'],
            'email' => $user['email']
        ];

        header('Location: ../profile.php');
    } else {
        $_SESSION['message'] = 'Не верный логин или пароль';
        header('Location: ../auth.php');
    }


?>


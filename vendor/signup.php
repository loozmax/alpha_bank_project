<?php
    session_start();
    require_once 'connect.php';

    $login = $_POST['login'];
    $password = $_POST['password'];
    $password_confirm = $_POST['password_confirm'];
    $email = $_POST['email'];


    if ($password == $password_confirm) {
        $password = md5($password);
        $data = [
            'id' => NULL,
            'login' => $login,
            'email' => $email,
            'password' => $password
        ];
        $sql = "INSERT INTO `registration` (`id`, `login`, `email`, `password`) VALUES (:id, :login, :email, :password)";
        $connect->prepare($sql)->execute($data);

        $_SESSION['message'] = 'Регистрация прошла успешна';
        header('Location: ../auth.php');
    } else {
        $_SESSION['message'] = 'Пароли не совпадают';
        header('Location: ../registration.php');
    }

?>

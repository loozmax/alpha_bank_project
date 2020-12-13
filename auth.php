<?php
    session_start();

    if ($_SESSION['user']) {
        header('Location: profile.php');
    }
?>


<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="style.css" />
</head>
<body>
<main>
    <!-- Форма авторизации -->
    <form class="auth-form" method="post" action="vendor/signin.php">
        <label>Введите имя или почту</label>
        <input type="text" name="login" placeholder="Введи свое логин или почту">
        <label>Введите пароль</label>
        <input type="password" name="password" placeholder="Введите пароль">
        <button type="submit">Войти</button>
        <p>
            Нет аккаунта? - <a href="registration.php">зарегистрируйтесь</a>
        </p>
        <?php
        if($_SESSION['message']) {
            echo '<p class="msg">'.$_SESSION['message'].'</p>';
        }
        unset($_SESSION['message']);
        ?>
    </form>
</main>
</body>
</html>
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

    <!-- Форма регистрации -->
<main>
    <form method="post" action="vendor/signup.php">
        <label>Придумайте имя</label>
        <input type="text" name="login" placeholder="Введи свое имя">
        <label>Укажите электронную почту</label>
        <input type="email" name="email" placeholder="Введи свою электронную почту">
        <label>Введите пароль</label>
        <input type="password" name="password" placeholder="Введите пароль">
        <label>Подтвердите пароль</label>
        <input type="password" name="password_confirm" placeholder="Введи пароль снова">
        <button type="submit">Зарегистрироваться</button>
        <p>
            Уже есть аккаунт? - <a href="auth.php">авторизуйтесь</a>
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

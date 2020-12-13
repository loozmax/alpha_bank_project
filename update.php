<?php
    require_once 'vendor/connect.php';
    $product_id = $_GET['id'];
    $number = explode(',', $product_id);
    $id = $number[0];
    $date = $number[1];
    $product = $connect->query("SELECT * FROM message WHERE id = '$id'");
    $product = $product->fetch(PDO::FETCH_ASSOC);
?>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="container">
    <form class="update-form" action="vendor/update.php" method="post">
        <input type="hidden" value="<?= $product['id'] .','. $product['date'] ?>" name="id">
        <input type="text" name="title" value="<?= $product['message'] ?>">
        <button class="update" type="submit">Отредактировать</button>
        <button class="cancel"><a href="profile.php">Отменить</a></button>
    </form>
</div>
</body>
</html>

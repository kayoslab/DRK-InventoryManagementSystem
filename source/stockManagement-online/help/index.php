<?php
    /*
     * Copyright (c) - All Rights Reserved
     *
     * Unauthorized copying of these files, via any medium is
     * strictly prohibited Proprietary and confidential
     *
     * NOTICE:
     * All information contained in this project is, and remains the property of the owner and its suppliers, if any.
     * The intellectual and technical concepts contained herein are proprietary to the owner and its suppliers and
     * are protected by trade secret or copyright law. Dissemination of this information or reproduction of this
     * material is strictly forbidden unless prior written permission is obtained by the owner.
     *
     * @author
     *
     */
?>
<html lang="en">
<!DOCTYPE html>
<head>
    <title>Stock Manager</title>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=0.5">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap core CSS -->
    <link href="./../css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <ul class="nav navbar-nav">
            <li role="presentation"><a href="./index.php">Home</a></li>
            <li role="presentation"><a href="./index.php?id=1">Einrichtung</a></li>
            <li role="presentation"><a href="./index.php?id=2">Login</a></li>
            <li role="presentation"><a href="./index.php?id=3">Hauptmenü</a></li>
            <li role="presentation"><a href="./index.php?id=4">Objektdaten</a></li>
            <li role="presentation"><a href="./index.php?id=5">Detailansicht</a></li>
            <li role="presentation"><a href="./index.php?id=6">Inventarliste</a></li>
            <li role="presentation"><a href="./index.php?id=7">Einstellungen</a></li>
            <li role="presentation"><a href="./index.php?id=8">Meldungen</a></li>
            <li role="presentation"><a href="./index.php?id=9">Hinzufügen/Bearbeiten</a></li>
        </ul>
    </div>
    </nav>
</br></br></br></br>
    <div class = "container">
    <?php
        if (isset($_GET['id'])) {
            switch ($_GET['id']) {
                case 0:
                include '0.php';
                break;
                case 1:
                include '1.php';
                break;
                case 2:
                include '2.php';
                break;
                case 3:
                include '3.php';
                break;
                case 4:
                include '4.php';
                break;
                case 5:
                include '5.php';
                break;
                case 6:
                include '6.php';
                break;
                case 7:
                include '7.php';
                break;
                case 8:
                include '8.php';
                break;
                case 9:
                include '9.php';
                break;
            }
        } else {
            include '0.php';
        }
    ?>
    </div>
</body>
</html>

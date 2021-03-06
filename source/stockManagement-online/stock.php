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

     ob_start();
     session_start();
?>
<html lang="en">
<!DOCTYPE html>
    <?php include 'head.php'; ?>
    <?php
        $msg = '';
        if (isset($_POST['submit']) && !empty($_POST['username']) && !empty($_POST['password'])) {
            include 'connect.php';
            $sql = "SELECT `id`, `username`, `password` FROM `User` WHERE `username` = '" . $_POST['username'] . "';";
            $result = mysql_query($sql);
            if ($result) {
                // while I have more results, loop through them
                // returning each result as an array
                while ($user = mysql_fetch_array($result)) {
                    if (md5($_POST['password']) === $user['password']) {
                        $_SESSION['valid'] = TRUE;
                        $_SESSION['timeout'] = time();
                        $_SESSION['userId'] = $user['id'];
                        break;
                    }
                }
                if(isset($_SESSION['valid']) == FALSE ) {
                    $msg = 'Username oder Password fehlerhaft.';
                }
            }
        }
        if(isset($_SESSION['valid']) == FALSE ) {
            include 'login.php';
        } else {
            echo "<body> \n";
            include 'nav.php';
            echo "<div class = \"container\"> \n";
            echo "</br> \n";

            if (isset($_GET['type'])) {
                include 'connect.php';
                echo "</br> \n </br> \n";

                $sql = "SELECT `so`.`id`, `so`.`title`, `so`.`totalVolume`, `so`.`typeId`, `so`.`mtkIntervall`, `so`.`stkIntervall`
                FROM `StockObject` `so`
                WHERE `so`.`typeId` = " . $_GET['type'] ." ORDER BY `so`.`id` ASC;";
                $result = mysql_query($sql);
                if ($result && mysql_num_rows($result) > 0) {
                    echo "<div class=\"panel panel-default\"> \n";
                    echo "<div class=\"panel-heading\"> \n";

                    switch ($_GET['type']) {
                    case 1:
                        echo "<h3 class=\"panel-title\">Geräte</h3> \n";
                        break;
                    case 2:
                        echo "<h3 class=\"panel-title\">Medizinisches Material</h3> \n";
                        break;
                    case 3:
                        echo "<h3 class=\"panel-title\">Versorgungsmaterial</h3> \n";
                        break;
                    default:
                        break;
                    }
                    echo "</div> \n";
                    switch ($_GET['type']) {
                    case 1:
                        echo "<table class=\"table table-bordered table-hover\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">";
                        echo "<colgroup>\n";
                        echo "<col width=\"10%\" />\n";
                        echo "<col width=\"30%\" />\n";
                        echo "<col width=\"20%\" />\n";
                        echo "<col width=\"20%\" />\n";
                        echo "<col width=\"20%\" />\n";
                        echo "</colgroup>";
                        echo "<tr><th>id</th> <th>Titel</th> <th>MTK Intervall</th> <th>STK Intervall</th> <th>Lagerbestand</th> </tr> \n";
                        break;
                    case 2:
                        echo "<table class=\"table table-bordered table-hover\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">";
                        echo "<colgroup>\n";
                        echo "<col width=\"10%\" />\n";
                        echo "<col width=\"70%\" />\n";
                        echo "<col width=\"20%\" />\n";
                        echo "</colgroup>";
                        echo "<tr><th>id</th> <th>Titel</th> <th>Lagerbestand</th> </tr> \n";
                        break;
                    case 3:
                        echo "<table class=\"table table-bordered table-hover\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">";
                        echo "<colgroup>\n";
                        echo "<col width=\"10%\" />\n";
                        echo "<col width=\"70%\" />\n";
                        echo "<col width=\"20%\" />\n";
                        echo "</colgroup>";
                        echo "<tr><th>id</th> <th>Titel</th> <th>Lagerbestand</th> </tr> \n";
                        break;
                    default:
                        break;
                    }
                    while ($stockValue = mysql_fetch_array($result)) {
                        echo "<tr>".
                        "<th>" . $stockValue['id'] . "</th>" .
                        "<td><a href=\"./object.php?id=" . $stockValue['id'] . "\">" . utf8_encode($stockValue['title'])  . "</a></td>";

                        switch ($stockValue['typeId']) {
                        case 1:
                            echo "<td>" . $stockValue['mtkIntervall'] . "</td>".
                            "<td>" . $stockValue['stkIntervall'] . "</td>".
                            "<td>" . $stockValue['totalVolume'] . "</td>";
                            break;
                        case 2:
                            echo "<td>" . $stockValue['totalVolume'] . "</td>";
                            break;
                        case 3:
                            echo "<td>" . $stockValue['totalVolume'] . "</td>";
                            break;
                        default:
                            break;
                        }
                        echo "</td></tr> \n";
                    }
                    echo "</table> \n";
                    echo "</div> \n";
                } else {
                    echo "<div class=\"panel panel-default\"> \n";
                    echo "<div class=\"panel-heading\"> \n";
                    echo "<h3 class=\"panel-title\">Inventar</h3> \n";
                    echo "</div> \n";
                        echo "<div class=\"row\"> \n";
                            echo "</br> \n";
                            echo "<p class=\"text-center\">Keine Einträge vorhanden</p> \n";
                            echo "</br> \n";
                            echo "</br> \n";
                        echo "</div> \n";
                    echo "</div> \n";
                }
            } else {
                // include '';
            }
            echo "</div> <!-- /container -->";
            echo "<!--<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js\"></script>-->";
            echo "<script>window.jQuery || document.write('<script src=\"./js/vendor/jquery.min.js\"><\/script>')</script>";
            echo "<script src=\"./js/bootstrap.min.js\"></script>";
            echo "</body>";
        }
    ?>
</html>

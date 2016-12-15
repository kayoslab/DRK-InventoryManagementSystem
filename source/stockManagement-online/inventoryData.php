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
<body>
    <?php include 'nav.php'?>
    <div class = "container">
        </br>
        <?php
                include 'connect.php';
                echo "</br> \n </br> \n";

                $sql = "SELECT `sv`.`id`,`so`.`id` as `object`, `so`.`title`, `sv`.`volume`, `lo`.`title` as `location`, `sv`.`messageId`, `so`.`typeId` FROM `StockValue` `sv` INNER JOIN `StockObject` `so` ON (`sv`.`stockObjectId` = `so`.`id`) INNER JOIN `Location` `lo` ON (`sv`.`locationId` = `lo`.`id`)  ORDER BY `so`.`typeId` ASC;";
                $result = mysql_query($sql);
                if ($result && mysql_num_rows($result) > 0) {
                    echo "<div class=\"panel panel-default\"> \n";
                    echo "<div class=\"panel-heading\"> \n";
                    echo "<h3 class=\"panel-title\">Inventar</h3> \n";
                    echo "</div> \n";
                    echo "<table class=\"table table-bordered table-hover\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">";
                    echo "<colgroup>\n";
                    echo "<col width=\"35%\" />\n";
                    echo "<col width=\"35%\" />\n";
                    echo "<col width=\"10%\" />\n";
                    echo "<col width=\"20%\" />\n";
                    echo "</colgroup>";
                    echo "<tr><th>Titel</th> <th>Lagerort</th> <th>Bestand</th> <th>Art</th> </tr> \n";
                    $type = array("Gerät", "Medizinisches Material", "Versorgungsmaterial");
                    while ($stockValue = mysql_fetch_array($result)) {
                        switch ($stockValue['messageId']) {
                        case 2:
                            echo "<tr class=\"warning\">" .
                            "<td><a href=\"./object.php?id=" . $stockValue['object'] . "\">" . utf8_encode($stockValue['title'])  . "</a></td>" .
                            "<td>" . utf8_encode($stockValue['location']) . "</td>".
                            "<td>" . $stockValue['volume'] . "</td>".
                            "<td><a href=\"./stock.php?type=" . $stockValue['typeId'] . "\">" . $type[$stockValue['typeId'] - 1] . "</a>" .
                            "</td></tr> \n";
                            break;
                        case 3:
                            echo "<tr class=\"danger\">" .
                            "<td><a href=\"./object.php?id=" . $stockValue['object'] . "\">" . utf8_encode($stockValue['title'])  . "</a></td>" .
                            "<td>" . utf8_encode($stockValue['location']) . "</td>".
                            "<td>" . $stockValue['volume'] . "</td>".
                            "<td><a href=\"./stock.php?type=" . $stockValue['typeId'] . "\">" . $type[$stockValue['typeId'] - 1] . "</a>" .
                            "</td></tr> \n";
                            break;
                        default:
                            echo "<tr>" .
                            "<td><a href=\"./object.php?id=" . $stockValue['object'] . "\">" . utf8_encode($stockValue['title'])  . "</a></td>" .
                            "<td>" . utf8_encode($stockValue['location']) . "</td>".
                            "<td>" . $stockValue['volume'] . "</td>".
                            "<td><a href=\"./stock.php?type=" . $stockValue['typeId'] . "\">" . $type[$stockValue['typeId'] - 1] . "</a>" .
                            "</td></tr> \n";
                            break;
                        }
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
        ?>
    </div> <!-- /container -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="./js/vendor/jquery.min.js"><\/script>')</script>
    <script src="./js/bootstrap.min.js"></script>
</body>

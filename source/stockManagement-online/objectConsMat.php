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

                $sql = "SELECT
                        `so`.`id`,
                        `so`.`title`,
                        `so`.`description`,
                        `so`.`totalVolume`
                        FROM `StockObject` `so`
                        WHERE `so`.`id` = " . $_GET['id'] . ";";
                $result = mysql_query($sql);
                $row = mysql_fetch_array($result);
                if ($result) {
                    echo "<div class=\"panel panel-info\"> \n";
                    echo "<div class=\"panel-heading\"> \n";
                    echo "<h3 class=\"panel-title\">Materialdaten</h3> \n";
                    echo "</div> \n";
                        echo "<div class=\"row\"> \n";
                            echo "<div class=\"col-md-4 col-md-offset-1\"> \n";
                                echo "</br> \n";
                                echo "<b>Titel:</b> \n";
                                echo "</br> \n";
                                echo "</br> \n";
                            echo "</div> \n";
                            echo "<div class=\"col-md-4 col-md-offset-2\"> \n";
                                echo "</br> \n";
                                echo " " . utf8_encode($row['title']) . " \n";
                                echo "</br> \n";
                                echo "</br> \n";
                            echo "</div> \n";
                        echo "</div> \n";
                        echo "<div class=\"row\"> \n";
                            echo "<div class=\"col-md-4 col-md-offset-1\"> \n";
                                echo "<b>Beschreibung:</b> \n";
                                echo "</br> \n";
                                echo "</br> \n";
                            echo "</div> \n";
                            echo "<div class=\"col-md-4 col-md-offset-2\"> \n";
                                echo " " . utf8_encode($row['description']) . " \n";
                                echo "</br> \n";
                                echo "</br> \n";
                            echo "</div> \n";
                        echo "</div> \n";
                    echo "</div> \n";
                }
        ?>
    </div> <!-- /container -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="./js/vendor/jquery.min.js"><\/script>')</script>
    <script src="./js/bootstrap.min.js"></script>
</body>

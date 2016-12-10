<?php
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
                        $_SESSION['valid'] = true;
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
            include 'connect.php';
            if (isset($_GET['id'])) {
                $sql = "SELECT
                        `so`.`id`,
                        `so`.`typeId`
                        FROM `StockObject` `so`
                        WHERE `so`.`id` = " . $_GET['id'] . ";";
                $result = mysql_query($sql);
                $row = mysql_fetch_array($result);
                if ($result) {
                    switch ($row['typeId']) {
                    case 1:
                        include 'objectDevice.php';
                        break;
                    case 2:
                        include 'objectMedMat.php';
                        break;
                    case 3:
                        include 'objectConsMat.php';
                        break;
                    default:
                        // include '';
                        break;
                    }
                }

            } else {
                // include '';
            }
        }
    ?>
</html>

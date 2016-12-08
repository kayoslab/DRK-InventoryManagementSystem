<?php
   ob_start();
   session_start();
?>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Stock Manager</title>
        <?php include 'head.php';?>
    </head>

    <body>
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
                include 'base.php';
            }
        ?>

    </body>
</html>

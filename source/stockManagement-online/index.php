<?php
   ob_start();
   session_start();
?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Stock Manager</title>
    <!-- Bootstrap core CSS -->
    <link href="./css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="./css/signin.css" rel="stylesheet">
  </head>

  <body>
     <div class = "container form-signin">
        <h2 class="form-signin-heading">Anmelden</h2>
        <?php
            $msg = '';
            if (isset($_POST['login']) && !empty($_POST['username']) && !empty($_POST['password'])) {
               if ($_POST['username'] == 'hello' && $_POST['password'] == 'world') {
                  $_SESSION['valid'] = true;
                  $_SESSION['timeout'] = time();
                  $_SESSION['username'] = 'hello';
                  echo 'You have entered valid use name and password';
                  $msg = 'Nice!';
               } else {
                  $msg = 'Wrong username or password';
               }
            }
        ?>
     </div>
     <div class = "container">
       <form class = "form-signin" role = "form" action = "<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>" method = "post">
          <h4 class="form-signin-heading"><?php echo $msg; ?></h4>

          <!--<label for="username" class="sr-only">Benutzername</label>-->
          <input type="username" name="username" class="form-control" placeholder="Benutzername" required autofocus>

          <!--<label for="password" class="sr-only">Passwort</label>-->
          <input type="password" name="password" class="form-control" placeholder="Passwort" required>

          <button class="btn btn-lg btn-primary btn-block" type="submit">Einloggen</button>
       </form>
     </div> <!-- /container -->
  </body>
</html>

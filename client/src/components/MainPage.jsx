import React, { useState } from 'react';
import { createShortLink } from '../services/ShortLinkService';
import TextField from '@material-ui/core/TextField';
import { Paper } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import Link from '@material-ui/core/Link';
import { Link as RouterLink } from 'react-router-dom';
import HomeIcon from '@material-ui/icons/Home';
import { isAuthenticated, logout } from '../services/AuthService';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { toast } from 'react-toastify';


const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    backgroundColor: '#f0f0f0',
    minHeight: '100vh',
    paddingTop: theme.spacing(12)
  },
  paper: {
    marginTop: theme.spacing(12),
    margin: 'auto',
  },
  paper1: {
    padding: theme.spacing(8),
    margin: 'auto',
    marginTop: '30px',
    marginBottom: 0,
  },
  whiteBackground: {
    '& .MuiInputBase-input': {
      backgroundColor: '#ffffff',
    },
  },
  image: {
    width: 128,
    height: 128,
  },
  img: {
    margin: 'auto',
    display: 'block',
    maxWidth: '100%',
    maxHeight: '100%',
  },
  topLeftLink: {
    position: 'absolute',
    top: theme.spacing(2),
    left: theme.spacing(2),
  },
  topRigthLink: {
    position: 'absolute',
    top: theme.spacing(2),
    right: theme.spacing(2),
  },
  button: {
    width: '150px',
    height: '40px',
    fontSize: '14px',
    backgroundColor: 'white',
    color: 'black',
    '&:hover': {
      backgroundColor: '#f5f5f5',
      color: 'black',
    },
  },
  link: {
    color: 'black',
    fontSize: '20px',
    textDecoration: 'none',
    marginBottom: '5px',
  },
  homeIcon: {
    marginRight: theme.spacing(1), 
    fontSize: 55,
  },
}));


const MainPage = () => {
  const [inputValue, setInputValue] = useState('');
  const [shortLink, setShortLink] = useState('');
  const [loginError, setLoginError] = useState('');
  const [shortenError, setShortenError] = useState('');
  const [shortened, setShortened] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(isAuthenticated());
  const classes = useStyles();

  const handleChange = (event) => {
    setInputValue(event.target.value);
    setShortened(false);
  };


  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const accessToken = localStorage.getItem('accessToken');

      if (!accessToken) {
        setLoginError('Ошибка аутентификации. Войдите, чтобы создать короткую ссылку.');
        console.error('Ошибка аутентификации.')
        return; 
      }

      setLoginError('');

      const shortLinkResult = await createShortLink(inputValue, accessToken);
      setShortLink(shortLinkResult);
      setShortenError(''); 
      setShortened(true);

    } catch (error) {
      console.error('Ошибка при создании короткой ссылки.');
      setShortLink('');
      setShortenError('Ошибка при создании короткой ссылки.');
    }
  };

  const handleCopy = () => {
    if (shortLink) {
      navigator.clipboard.writeText(shortLink)
        .then(() => {
          toast.success('Скопирована!', {
            position: 'top-right', 
            autoClose: 2000, 
            hideProgressBar: true,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
        })
        .catch((error) => console.error('Не удалось скопировать: ', error));
    }
  };

  const handleLogout = () => {
    const accessToken = localStorage.getItem('accessToken'); 
    if (accessToken) {
      logout(accessToken); 
      setIsLoggedIn(false);
      toast.success('Вы успешно вышли из системы!', {
        position: 'top-right',
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
    }
  };

  return (
    <>
      <div className={classes.root}>

        <Container
          component='main'
          maxWidth='sm'
          disableGutters={true}
        >
          <CssBaseline />
          <div className={classes.paper}>
            <Typography
              component="h1"
              variant="h5"
              style={{ margin: '10px 0' }}
            >
              URL Shortener
            </Typography>
            <form onSubmit={handleSubmit}>
              <Grid container spacing={1}>
                <Grid item xs={9}>
                  <TextField
                    id="outlined-basic"
                    variant="outlined"
                    value={inputValue}
                    onChange={handleChange}
                    fullWidth
                    label="Введите URL для сокращения"
                    InputProps={{
                      className: classes.whiteBackground,
                    }}
                  />
                </Grid>
                <Grid item xs={3}>
                  <Button
                    type="submit"
                    variant="contained"
                    color="primary"
                    fullWidth
                    style={{ height: '100%' }}
                    disabled={shortened}
                  >
                    Сократить
                  </Button>
                </Grid>
              </Grid>
            </form>
          </div>

          {loginError && <p>{loginError}</p>}

          {shortenError && <p>{shortenError}</p>}

          {shortLink && (
            <div>
              <Paper className={classes.paper1}>
                <p>
                  <a
                    href={shortLink}
                    target="_blank"
                    rel="noopener noreferrer"
                    className={classes.link}
                  >
                    {shortLink}
                  </a>
                </p>
                <Button
                  className={classes.button}
                  variant="contained"
                  color="primary"
                  fullWidth
                  onClick={handleCopy}
                >
                  Копировать
                </Button>
              </Paper>
            </div>
          )}
        </Container>
      </div>

      <Link component={RouterLink} to="/" className={classes.topLeftLink}>
        <HomeIcon className={classes.homeIcon} />
      </Link>

      {isLoggedIn ? (
        <Button onClick={handleLogout} className={classes.topRigthLink} variant="contained" color="primary">
          Logout
        </Button>
      ) : (
        <Button component={RouterLink} to="/signin" className={classes.topRigthLink} variant="contained" color="primary">
          Login
        </Button>
      )}
    </>
  );
}

export default MainPage;

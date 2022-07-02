<template>
    <div>
    </div>
</template>

<script>
  export default {
    name: 'LoginAuto',
    mounted () {
      if (!this.$store.getters['auth/isCognitoUserLoggedIn']) {
        this.autoSignIn()
      } else {
        window.location.href = '/'
      }
    },
    methods: {
      parseJwt (token) {
        var base64Url = token.split('.')[1]
        var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
        var jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
          return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
        }).join(''))
        return JSON.parse(jsonPayload)
      },
      autoSignIn () {
        console.log('vx: autoSignIn called...')
        const urlSearchParams = new URLSearchParams(window.location.search)
        const params = Object.fromEntries(urlSearchParams.entries())
        var username = typeof params.username === 'undefined' ? null : params.username
        var accessToken = typeof params.access_token === 'undefined' ? null : params.access_token
        var idToken = typeof params.id_token === 'undefined' ? null : params.id_token
        var accessTokenDecoded = this.parseJwt(accessToken)
        var now = Date.now()
        var authTime = accessTokenDecoded.auth_time * 1000
        if (now - authTime < 15000) {
          if (username !== null && accessToken !== null && idToken !== null) {
            window.localStorage.clear()
            window.localStorage.setItem('CognitoIdentityServiceProvider.6012f1g1p1mrf67qjnl9gq1kq6.LastAuthUser', username)
            window.localStorage.setItem('CognitoIdentityServiceProvider.6012f1g1p1mrf67qjnl9gq1kq6.' + username + '.accessToken', accessToken)
            window.localStorage.setItem('CognitoIdentityServiceProvider.6012f1g1p1mrf67qjnl9gq1kq6.' + username + '.idToken', idToken)
          }
        }
        window.location.href = '/'
      },
    },
  }
</script>

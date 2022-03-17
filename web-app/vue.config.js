const path = require('path')

module.exports = {
  outputDir: path.resolve(__dirname, './src/main/resources/webroot'),
  pluginOptions: {
    prerenderSpa: {
      renderRoutes: [
        '/',
      ],
      useRenderEvent: true,
      headless: true,
      onlyProduction: true,
    },
  },
  transpileDependencies: [
    'vuetify',
  ],
  devServer: {
    proxy: {
      '^/auth/api': {
        target: 'https://api.honely.com',
        ws: true,
        changeOrigin: true,
      },
      '^/api': {
        target: 'https://api.honely.com',
        ws: true,
        changeOrigin: true,
      },
    },
  },
}

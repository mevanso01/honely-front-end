<template>
  <!-- eslint-disable -->
  <div class="section-maps">
    <!-- home-map-tabs -->
    <div class="home-map-tabs">
      <span class="home-map-tab active" data="map-migration">Heat Map</span>
      <span class="home-map-tab" data="map-accuracy">Accuracy</span>
    </div>
    <!-- /home-map-tabs -->

    <!-- home-map-section -->
    <div class="home-map-section">
      <!-- home-map-container -->
      <div
        id="map-accuracy"
        class="home-map-container"
      >
        <div class="home-map-title">
          Our Accuracy <span>(Property valuations and forecasts for 50 states)</span>
        </div>
        <div id="container-accuracy" class="map-wrapper" />
        <div class="explanation-row">
          <div class="explanation-box">
            <p><span class="explanation-note">MdAPE</span> - A standard measure of a valuation model's average deviation from the actual transaction values.</p>
            <p><span class="explanation-note">+/- 10 Percent</span> - How often our model's predicted price is within plus or minus 10 percent of the actual sale price.</p>
          </div>
        </div>
      </div>
      <!-- /home-map-container -->

      <!-- home-map-container -->
      <div
        id="map-migration"
        class="home-map-container active"
      >
        <div class="home-map-title">
          Moving Trends Heat Map <span>( Net Move in rate 1 Year Forecast)*</span>
        </div>
        <div id="container-migration" class="map-wrapper">
          <p class="map-loader"><i class="fas fa-spinner fa-spin"></i> <span>Loading map data, please wait...</span></p>
        </div>
        <div class="explanation-row">
          <div class=explanation-box>
            <div class="custom-heatmap-legend">
              <div class="custom-legend-title">Move-in Rate**</div>
              <div class="custom-legend-color-bar">&nbsp;</div>
              <div class="custom-legend-label-wrapper">
                <label>Low</label>
                <label>High</label>
              </div>
            </div>
            <p>*Annualized Monthly Net Move-In Rate, 1 year from now</p>
            <p>**Higher value indicates more people moving in and lower value indicates more people moving out</p>
          </div>
        </div>
        <div class="map-buttons-row">
          <button
            id="map-button-current"
            class="map-buttons"
            @click="doUpdateMigrationMap('current')"
          >
            View Current Year Data
          </button>
          <button
            id="map-button-1yr"
            class="map-buttons"
            @click="doUpdateMigrationMap('1year')"
          >
            View 1 Year Data
          </button>
          <button
            id="map-button-2yr"
            class="map-buttons"
            @click="doUpdateMigrationMap('2year')"
          >
            View 2 Year Data
          </button>
          <button
            id="map-button-3yr"
            class="map-buttons"
            @click="doUpdateMigrationMap('3year')"
          >
            View 3 Year Data
          </button>
        </div>
      </div>
      <!-- /home-map-container -->
    </div>
    <!-- /home-map-section -->
    <div class="mobile-only-section">
      <img :src="require('@/assets/home-section-img.jpg')" />
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  /* eslint-disable */
  // import $ from 'jquery'
  var accuracyData = []
  var migrationData = []
  export default {
    name: 'SectionHeatMap',
    data: () => ({}),

    created () {
      // default
    },

    mounted () {
      this.doRenderAccuracyMap()
      this.doRenderMigrationMap()
    },

    methods: {
      doRenderAccuracyMap () {
        fetch('https://api.honely.com/lookup/accuracy_statistics_new')
          .then(response => response.json())
          .then(data => {
            // console.log(data)
            accuracyData = data.accuracy_statistics
            this.doUpdateAccuracyMap ()
          }).catch(error => {
            console.log('[ERROR] heatmap fetch error.')
            console.log(error)
          })
      },
      doUpdateAccuracyMap () {
        // Create the chart
        Highcharts.mapChart('container-accuracy', {
          chart: {
              map: 'countries/us/us-all',
              backgroundColor: 'transparent',
              pinchType: 'none',
          },
          title: {
              text: '',
              style: {
                fontWeight: 'bold',
                fontSize: '25px',
                letterSpacing: '0.01px',
              },
          },
          tooltip: {
            useHTML: true,
            borderRadius: 50,
            formatter: function () {
              return this.point.options.value
            }
          },
          mapNavigation: {
              enabled: true,
              buttonOptions: {
                  verticalAlign: 'top'
              },
              enableMouseWheelZoom: false
          },
          plotOptions: {
            map: {
              allAreas: false,
            }
          },
          colorAxis: {
            dataClasses: [
              {
                color: "#e67e23"
              }
            ]
          },
          legend: {
            enabled: false
          },
          navigation: {
            buttonOptions: {
              enabled: false
            }
          },
          credits: {
            enabled: false
          },
          series: [
            {
              data: accuracyData,
              name: 'ACCURACY DATA',
              states: {
                  hover: {
                      color: '#06a550'
                  }
              },
              dataLabels: {
                  enabled: false,
                  format: '{point.name}'
              },
              tooltip: {
                useHTML: true,
              },
              borderColor: '#000000',
          }, 
          {
              name: 'Separators',
              type: 'mapline',
              data: Highcharts.geojson(Highcharts.maps['countries/us/us-all'], 'mapline'),
              color: 'silver',
              nullColor: 'silver',
              showInLegend: false,
              enableMouseTracking: false
          }
        ]
        })   
      },
      doRenderMigrationMap () {
        if(migrationData.length!=0){
          this.doUpdateMigrationMap('1year')
        } else {
        fetch('https://api.honely.com/lookup/heatmap')
          .then(response => response.json())
          .then(data => {
            // console.log(data)
            migrationData = data 
            this.doUpdateMigrationMap('1year')
          }).catch(error => {
            console.log('[ERROR] heatmap fetch error.')
            console.log(error)
          })
        }
      },
      doUpdateMigrationMap (version) {
        const netIn = []
        let yearLabel = '1 Year'
        // clear active class
        const buttons = document.getElementsByClassName('map-buttons')
        for (let i = 0; i < buttons.length; i++) {
          buttons[i].className = buttons[i].className.replace('active', '')
        }
        if (version === '1year') {
          migrationData.forEach(item => {
            const obj = {}
            obj.code = item.code
            obj.name = item.county_name
            if (item.one_year_net_in != null) {
              obj.value = Number(parseFloat(item.one_year_net_in).toFixed(2))
            } else {
              obj.value = Number(0)
            }
            netIn.push(obj)
            yearLabel = '1 Year'
            document.getElementById('map-button-1yr').classList.add('active')
          })
          // console.log(netIn)
        } else if (version === '2year') {
          migrationData.forEach(item => {
            const obj = {}
            obj.code = item.code
            obj.name = item.county_name
            if (item.two_year_net_in != null) {
              obj.value = Number(parseFloat(item.two_year_net_in).toFixed(2))
            } else {
              obj.value = Number(0)
            }
            netIn.push(obj)
            yearLabel = '2 Year'
            document.getElementById('map-button-2yr').classList.add('active')
          })
          // console.log(netIn)
        } else if (version === '3year') {
          migrationData.forEach(item => {
            const obj = {}
            obj.code = item.code
            obj.name = item.county_name
            if (item.three_year_net_in != null) {
              obj.value = Number(parseFloat(item.three_year_net_in).toFixed(2))
            } else {
              obj.value = Number(0)
            }
            netIn.push(obj)
            yearLabel = '3 Year'
            document.getElementById('map-button-3yr').classList.add('active')
          })
        } else if (version === 'current') {
          migrationData.forEach(item => {
            const obj = {}
            obj.code = item.code
            obj.name = item.county_name
            if (item.current_net_in != null) {
              obj.value = Number(parseFloat(item.current_net_in).toFixed(2))
            } else {
              obj.value = Number(0)
            }
            netIn.push(obj)
            yearLabel = 'Current Year'
            document.getElementById('map-button-current').classList.add('active')
          })
        }
        const countiesMap = Highcharts.geojson(
          Highcharts.maps['countries/us/us-all-all'],
        )
        // Extract the line paths from the GeoJSON
        const lines = Highcharts.geojson(
          Highcharts.maps['countries/us/us-all-all'], 'mapline',
        )
        // Filter out the state borders and separator lines, we want these
        // in separate series
        const borderLines = lines.filter(
          l => l.properties['hc-group'] === '__border_lines__',
        )
        const separatorLines = lines.filter(
          l => l.properties['hc-group'] === '__separator_lines__',
        )
        // Add state acronym for tooltip
        countiesMap.forEach(function (mapPoint) {
          mapPoint.name = mapPoint.name + ', ' + mapPoint.properties['hc-key'].substr(3, 2).toUpperCase()
        })
        // render map
        document.getElementById('container-migration').innerHTML = ''
        // Create the map
        setTimeout(function () { // Otherwise innerHTML doesn't update
          Highcharts.mapChart('container-migration', {
            chart: {
              borderWidth: 0,
              borderColor: '#cccccc',
              backgroundColor: 'transparent',
              marginRight: 20,
              pinchType: 'none',
            },
            title: {
              text: '',
              style: {
                fontWeight: 'bold',
                // color: 'white',
                fontSize: '30px',
                letterSpacing: '0.01 em',
              },
            },
            credits: {
              enabled: false,
            },
            legend: {
              enabled: false,
              layout: 'horizontal',
              verticalAlign: 'bottom',
              title: {
                text: 'Net-in rate<sup>**</sup>'
              },
              align: 'right',
              floating: true,
              backgroundColor: ( // theme
                Highcharts.defaultOptions &&
                Highcharts.defaultOptions.legend &&
                Highcharts.defaultOptions.legend.backgroundColor
              ) || 'rgba(255, 255, 255, 0.85)',
            },
            mapNavigation: {
              enabled: true,
              enableMouseWheelZoom: false,
              buttonOptions: {
                  verticalAlign: 'top',
              },
            },
            colorAxis: {
              min: -0.22,
              max: 0.22,
              tickInterval: 0.02,
              stops: [[0.0, '#0000ff'], [0.01, '#0505ff'], [0.02, '#0a0aff'], [0.03, '#0f0fff'], [0.04, '#1414ff'], [0.05, '#1919ff'], [0.06, '#1e1eff'], [0.07, '#2323ff'], [0.08, '#2828ff'], [0.09, '#2d2dff'], [0.1, '#3232ff'], [0.11, '#3838ff'], [0.12, '#3d3dff'], [0.13, '#4242ff'], [0.14, '#4747ff'], [0.15, '#4c4cff'], [0.16, '#5151ff'], [0.17, '#5656ff'], [0.18, '#5b5bff'], [0.19, '#6060ff'], [0.2, '#6565ff'], [0.21, '#6a6aff'], [0.22, '#6f6fff'], [0.23, '#7474ff'], [0.24, '#7979ff'], [0.25, '#7e7eff'], [0.26, '#8383ff'], [0.27, '#8888ff'], [0.28, '#8d8dff'], [0.29, '#9292ff'], [0.3, '#9797ff'], [0.31, '#9d9dff'], [0.32, '#a2a2ff'], [0.33, '#a7a7ff'], [0.34, '#acacff'], [0.35, '#b1b1ff'], [0.36, '#b6b6ff'], [0.37, '#bbbbff'], [0.38, '#c0c0ff'], [0.39, '#c5c5ff'], [0.4, '#cacaff'], [0.41, '#cfcfff'], [0.42, '#d4d4ff'], [0.43, '#d9d9ff'], [0.44, '#dedeff'], [0.45, '#e3e3ff'], [0.46, '#e8e8ff'], [0.47, '#ededff'], [0.48, '#f2f2ff'], [0.49, '#f7f7ff'], [0.5, '#fcfcff'], [0.5, '#fffcfc'], [0.51, '#fff7f7'], [0.52, '#fff2f2'], [0.53, '#ffeded'], [0.54, '#ffe8e8'], [0.55, '#ffe3e3'], [0.56, '#ffdede'], [0.57, '#ffd9d9'], [0.58, '#ffd4d4'], [0.59, '#ffcfcf'], [0.6, '#ffcaca'], [0.61, '#ffc5c5'], [0.62, '#ffc0c0'], [0.63, '#ffbbbb'], [0.64, '#ffb6b6'], [0.65, '#ffb1b1'], [0.66, '#ffacac'], [0.67, '#ffa7a7'], [0.68, '#ffa2a2'], [0.69, '#ff9d9d'], [0.7, '#ff9797'], [0.71, '#ff9292'], [0.72, '#ff8d8d'], [0.73, '#ff8888'], [0.74, '#ff8383'], [0.75, '#ff7e7e'], [0.76, '#ff7979'], [0.77, '#ff7474'], [0.78, '#ff6f6f'], [0.79, '#ff6a6a'], [0.8, '#ff6565'], [0.81, '#ff6060'], [0.82, '#ff5b5b'], [0.83, '#ff5656'], [0.84, '#ff5151'], [0.85, '#ff4c4c'], [0.86, '#ff4747'], [0.87, '#ff4242'], [0.88, '#ff3d3d'], [0.89, '#ff3838'], [0.9, '#ff3232'], [0.91, '#ff2d2d'], [0.92, '#ff2828'], [0.93, '#ff2323'], [0.94, '#ff1e1e'], [0.95, '#ff1919'], [0.96, '#ff1414'], [0.97, '#ff0f0f'], [0.98, '#ff0a0a'], [0.99, '#ff0505'], [1.0, '#ff0000']],
              labels: {
                format: '{value}%',
              },
            },
            plotOptions: {
              mapline: {
                showInLegend: false,
                enableMouseTracking: false,
              },
            },
            exporting: {
              enabled: false,
            },
            series: [{
              mapData: countiesMap,
              data: netIn,
              joinBy: ['hc-key', 'code'],
              name: 'Move-in rate',
              tooltip: {
                valueSuffix: '%',
              },
              borderWidth: 0.5,
              states: {
                hover: {
                  color: '#a4edba',
                },
              },
              shadow: false,
            }, {
              type: 'mapline',
              name: 'State borders',
              data: borderLines,
              color: 'white',
              shadow: false,
            }, {
              type: 'mapline',
              name: 'Separator',
              data: separatorLines,
              color: 'gray',
              shadow: false,
            }],
          })
        }, 0)
      },
    },
  }
</script>

<style>
.section-maps {
  padding-top: 2rem;
  background: #f9f9f9;
}
.mobile-only-section {
  display: none;
  width: 100%;
}
.home-map-tabs {
  width: 100%;
  display: flex;
  justify-content: center;
}
.home-map-tabs span {
    display: block;
    line-height: 100%;
    color: #bbbbbb;
    background: #ffffff;
    text-transform: uppercase;
    padding: 14px 15px;
    margin-right: 1rem;
    cursor: pointer;
    border: 1px solid #bbbbbb;
    border-radius: 3px;
}
.home-map-tabs span:hover {
  color: #ffffff;
  background: #00a650;
  border-color: #00a650;
}
.home-map-tabs span.active {
  color: #ffffff;
  background: #00a650;
  border-color: #00a650;
}
.home-map-tab span:last-of-type {
  margin-right: 0;
}
.home-map-section {
  padding: 2rem 0;
}
.home-map-container {
  display: none;
  max-width: 1300px;
  margin: 0 auto;
}
.home-map-container.active {
   display: block;
}
.home-map-title {
  font-size: 25px;
  font-weight: 600;
  text-align: center;
  margin-bottom: 2.5rem; 
}
.home-map-title span {
  font-size: 18px;
}
.map-wrapper {
  min-width: 310px;
  height: 600px; 
}
.explanation-row {
}
.explanation-box {
  margin-top: 2rem;
  border-radius: 5px;
  padding: 1rem;
  max-width: 440px;
  border: 1px solid #aaaaaa;
}
.explanation-row p {
  font-size: 15px;
}
.explanation-note {
  font-weight: bold;
  color: #07871c;
}
.custom-heatmap-legend {
  width: 100%;
  margin-bottom: 10px;
  padding-bottom: 10px;
  border-bottom: 1px solid #bbbbbb;
}
.custom-legend-title {
  font-weight: 600;
  font-size: 14px;
  padding-bottom: 8px;
}
.custom-legend-color-bar {
  background: rgb(18,18,255);
  background: linear-gradient(90deg, rgba(18,18,255,1) 0%, rgba(255,255,255,1) 50%, rgba(255,0,0,1) 100%);
  height: 12px;
  margin-bottom: 10px;
}
.custom-legend-label-wrapper {
  display: flex;
  justify-content: space-between;
}
.custom-legend-label-wrapper label {
  font-size: 14px;
  font-weight: 600;
}
.map-loader {
  color: #00a650;
  text-align: center;
  font-size: 20px;
  margin-bottom: 0 !important;
  padding: 1rem;
}
.map-loader span {
  padding-left: 5px;
}
.map-buttons-row {
  display: none;
}
@media (max-width: 1300px) {
  .explanation-row {
    width: 90%;
    margin: 0 auto;
  }
}
@media (max-width: 700px) {
  .map-wrapper {
    height: auto;
  }
  .home-map-title {
    font-size: 20px;
    margin-bottom: 0;
  }
  .home-map-title span {
    display: block;
    font-size: 15px;
  }
}
@media (max-width: 500px) {
  .mobile-only-section {
    display: block;
  }
  .mobile-only-section img {
    display: block;
    width: 100%;
    height: auto;
  }
}
</style>

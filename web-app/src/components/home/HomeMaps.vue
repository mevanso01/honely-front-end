<template>
  <!-- eslint-disable -->
  <!-- container-home-maps -->
  <div class="container-home-maps">
    <!-- map-selection -->
    <div class="map-selection">
      <button class="btn-map-selection active" id="migration">Migration</button>
      <button class="btn-map-selection" id="accuracy">Accuracy</button>
    </div>
    <!-- /map-selection -->

    <!-- container-maps -->
    <div class="container-maps">
      <div class="home-map-container" id="map-selec-migration">
        <p class="home-map-title">Moving Trends Heat Map <span>(Net Move in rate 1 Year Forecast)*</span></p>
        <div id="map-migration" class="home-map">
          <div class="map-loader-container">
            <span class='mdi mdi-loading mdi-spin spin map-loader'></span>
            <span class="map-loader-text">Loading map data, please wait...</span>
          </div>
        </div>
        <!-- map explanation -->
        <div class="explanation-row">
          <div class="explanation-box">
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
        <!-- /map explanation -->
      </div>
      <div class="home-map-container" id="map-selec-accuracy">
        <p class="home-map-title">Our Accuracy <span>(Property valuations and forecasts for 50 states)</span></p>
        <div id="map-accuracy" class="home-map">
          <div class="map-loader-container">
            <span class='mdi mdi-loading mdi-spin spin map-loader'></span>
            <span class="map-loader-text">Loading map data, please wait...</span>
          </div>
        </div>
        <!-- map explanation -->
        <div class="explanation-row">
          <div class="explanation-box">
            <p><span class="explanation-note">MdAPE</span> - A standard measure of a valuation model's average deviation from the actual transaction values.</p>
            <p><span class="explanation-note">+/- 10 Percent</span> - How often our model's predicted price is within plus or minus 10 percent of the actual sale price.</p>
          </div>
        </div>
        <!-- /map explanation -->
      </div>
    </div>
    <!-- /container-maps -->
  </div>
  <!-- /container-home-maps -->
  <!-- eslint-enable -->
</template>

<script>
  // need to disable eslint for highchart
  /* eslint-disable */
  var accuracyData = []
  var migrationData = []

  export default {
    name: 'HomeMaps',
    props: {
    },
    data: () => ({
    }),
    created () {
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
            this.doUpdateAccuracyMap()
          }).catch(error => {
            console.log('[ERROR] accuracy statistics API fetch error.')
            console.log(error)
          })
      },
      doUpdateAccuracyMap () {
        // Create the chart
        Highcharts.mapChart('map-accuracy', {
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
            },
          },
          mapNavigation: {
            enabled: true,
            buttonOptions: {
              verticalAlign: 'top',
            },
            enableMouseWheelZoom: false,
          },
          plotOptions: {
            map: {
              allAreas: false,
            },
          },
          colorAxis: {
            dataClasses: [
              {
                color: '#f79256',
              },
            ],
          },
          legend: {
            enabled: false,
          },
          navigation: {
            buttonOptions: {
              enabled: false,
            },
          },
          credits: {
            enabled: false,
          },
          series: [
            {
              data: accuracyData,
              name: 'ACCURACY DATA',
              states: {
                hover: {
                  color: '#f79256',
                },
              },
              dataLabels: {
                enabled: false,
                format: '{point.name}',
              },
              tooltip: {
                useHTML: true,
              },
              borderColor: '#eeeeee',
            },
            {
              name: 'Separators',
              type: 'mapline',
              data: Highcharts.geojson(Highcharts.maps['countries/us/us-all'], 'mapline'),
              color: 'silver',
              nullColor: 'silver',
              showInLegend: false,
              enableMouseTracking: false,
            },
          ],
        })
      },
      doRenderMigrationMap () {
        if (migrationData.length !== 0) {
          this.doUpdateMigrationMap('1year')
        } else {
          fetch('https://api.honely.com/lookup/heatmap')
            .then(response => response.json())
            .then(data => {
              // console.log(data)
              migrationData = data
              this.doUpdateMigrationMap('1year')
            }).catch(error => {
              console.log('[ERROR] heatmap API fetch error.')
              console.log(error)
            })
        }
      },
      doUpdateMigrationMap (version) {
        const colorcodes = [
          [0, '#F79256'], [0.01, '#F79459'], [0.02, '#F7965C'], [0.03, '#F79860'], [0.04, '#F79A63'], [0.05, '#F79C66'], [0.06, '#F79F6A'], [0.07, '#F8A16D'], [0.08, '#F8A371'], [0.09, '#F8A574'], [0.10, '#F8A777'], [0.11, '#F8A97B'], [0.12, '#F8AC7E'], [0.13, '#F9AE81'], [0.14, '#F9B085'], [0.15, '#F9B288'], [0.16, '#F9B48C'], [0.17, '#F9B78F'], [0.18, '#F9B992'], [0.19, '#FABB96'], [0.20, '#FABD99'], [0.21, '#FABF9C'], [0.22, '#FAC1A0'], [0.23, '#FAC4A3'], [0.24, '#FAC6A7'], [0.25, '#FBC8AA'], [0.26, '#FBCAAD'], [0.27, '#FBCCB1'], [0.28, '#FBCFB4'], [0.29, '#FBD1B8'], [0.30, '#FBD3BB'], [0.31, '#FBD5BE'], [0.32, '#FCD7C2'], [0.33, '#FCD9C5'], [0.34, '#FCDCC8'], [0.35, '#FCDECC'], [0.36, '#FCE0CF'], [0.37, '#FCE2D3'], [0.38, '#FDE4D6'], [0.39, '#FDE7D9'], [0.40, '#FDE9DD'], [0.41, '#FDEBE0'], [0.42, '#FDEDE3'], [0.43, '#FDEFE7'], [0.44, '#FEF1EA'], [0.45, '#FEF4EE'], [0.46, '#FEF6F1'], [0.47, '#FEF8F4'], [0.48, '#FEFAF8'], [0.49, '#FEFCFB'], [0.50, '#FFFFFF'], [0.51, '#FFFFFF'], [0.52, '#FAFDFB'], [0.53, '#F6FCF7'], [0.54, '#F1FBF3'], [0.55, '#EDFAEF'], [0.56, '#E9F9EC'], [0.57, '#E4F8E8'], [0.58, '#E0F7E4'], [0.59, '#DBF6E0'], [0.60, '#D7F5DD'], [0.61, '#D3F4D9'], [0.62, '#CEF3D5'], [0.63, '#CAF2D1'], [0.64, '#C6F1CE'], [0.65, '#C1F0CA'], [0.66, '#BDEFC6'], [0.67, '#B8EEC2'], [0.68, '#B4EDBF'], [0.69, '#B0ECBB'], [0.70, '#ABEBB7'], [0.71, '#A7EAB3'], [0.72, '#A3E9B0'], [0.73, '#9EE8AC'], [0.74, '#9AE7A8'], [0.75, '#95E6A4'], [0.76, '#91E5A1'], [0.77, '#8DE39D'], [0.78, '#88E299'], [0.79, '#84E195'], [0.80, '#7FE091'], [0.81, '#7BDF8E'], [0.82, '#77DE8A'], [0.83, '#72DD86'], [0.84, '#6EDC82'], [0.85, '#6ADB7F'], [0.86, '#65DA7B'], [0.87, '#61D977'], [0.88, '#5CD873'], [0.89, '#58D770'], [0.90, '#54D66C'], [0.91, '#4FD568'], [0.92, '#4BD464'], [0.93, '#47D361'], [0.94, '#42D25D'], [0.95, '#3ED159'], [0.96, '#39D055'], [0.97, '#35CF52'], [0.98, '#31CE4E'], [0.99, '#2CCD4A'], [1.00, '#28CC46'], [1.01, '#24CB43'],
        ];
        const netIn = []
        let yearLabel = '1 Year'
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
          })
          // console.log(netIn)
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
          })
          // console.log(netIn)
        }
        const countiesMap = Highcharts.geojson(
          Highcharts.maps['countries/us/us-all-all'],
        )
        // Extract the line paths from the GeoJSON
        const lines = Highcharts.geojson(
          Highcharts.maps['countries/us/us-all-all'], 'mapline',
        )
        // Filter out the state borders and separator lines, we want these in separate series
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
        document.getElementById('map-migration').innerHTML = ''
        // Create the map
        setTimeout(function () {
          Highcharts.mapChart('map-migration', {
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
                text: 'Net-in rate<sup>**</sup>',
              },
              align: 'right',
              floating: true,
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
              stops: colorcodes,
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
              color: '#24cb43',
              shadow: false,
            }, {
              type: 'mapline',
              name: 'Separator',
              data: separatorLines,
              color: '#24cb43',
              shadow: false,
            }],
          })
        }, 0)
      },
    },
  }
</script>

import { Component, OnInit } from '@angular/core';
import { StatisticsService } from '../statistics.service';
import * as echarts from 'echarts';

@Component({
  selector: 'jhi-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.scss'],
})
export class StatisticsComponent implements OnInit {
  constructor(private statisticsService: StatisticsService) {}

  ngOnInit(): void {
    const myDiv = document.getElementById('chart') as HTMLDivElement;
    const chart = echarts.init(myDiv);

    const myDiv2 = document.getElementById('chart2') as HTMLDivElement;
    const chart2 = echarts.init(myDiv2);

    const myDiv3 = document.getElementById('chart3') as HTMLDivElement;
    const chart3 = echarts.init(myDiv3);

    const options: any = {
      title: {
        text: 'Customized Pie',
        left: 'center',
        top: 20,
        textStyle: {
          color: '#000',
        },
      },
      tooltip: {
        trigger: 'item',
      },
      visualMap: {
        show: false,
        min: 80,
        max: 600,
        inRange: {
          colorLightness: [0, 1],
        },
      },
      series: [
        {
          name: 'Access From',
          type: 'pie',
          radius: '55%',
          center: ['50%', '50%'],
          data: [
            { value: 335, name: 'Direct' },
            { value: 310, name: 'Email' },
            { value: 274, name: 'Union Ads' },
            { value: 235, name: 'Video Ads' },
            { value: 400, name: 'Search Engine' },
          ].sort(function (a, b) {
            return a.value - b.value;
          }),
          roseType: 'radius',
          label: {
            color: '#000',
          },
          labelLine: {
            lineStyle: {
              color: '#000',
            },
            smooth: 0.2,
            length: 10,
            length2: 20,
          },
          itemStyle: {
            color: '#c23531',
            shadowBlur: 200,
            shadowColor: 'rgba(0, 0, 0, 0.5)',
          },
          animationType: 'scale',
          animationEasing: 'elasticOut',
          animationDelay: function () {
            return Math.random() * 200;
          },
        },
      ],
    };

    let options2: any = {
      tooltip: {
        trigger: 'item',
      },
      legend: {
        top: '5%',
        left: 'center',
      },
      series: [
        {
          name: 'User Gender',
          type: 'pie',
          radius: ['40%', '70%'],
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: '10px',
            borderColor: '#fff',
            borderWidth: 10,
          },
          label: {
            show: false,
            position: 'center',
          },
          emphasis: {
            label: {
              show: true,
              color: 'black',
              fontSize: 40,
              fontWeight: 'bold',
            },
          },
          labelLine: {
            show: false,
          },
          data: [
            { value: 1048, name: 'Male' },
            { value: 735, name: 'Female' },
          ],
        },
      ],
    };

    let options3: any = {
      xAxis: {
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      },
      yAxis: {
        type: 'value',
      },
      series: [
        {
          data: [120, 200, 150, 80, 70, 110, 130],
          type: 'bar',
          showBackground: true,
          backgroundStyle: {
            color: 'rgba(180, 180, 180, 0.2)',
          },
        },
      ],
    };

    chart.setOption(options);
    chart2.setOption(options2);
    chart3.setOption(options3);
  }
}

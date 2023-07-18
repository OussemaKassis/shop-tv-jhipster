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

    const myDiv4 = document.getElementById('chart4') as HTMLDivElement;
    const chart4 = echarts.init(myDiv4);

    const seriesLabel = {
      show: true,
    };
    const option4: any = {
      title: {
        text: 'Plans Income',
        top: 20,
        left: 20,
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow',
        },
      },
      legend: {
        data: ['Free Trail', 'Standard', 'Unlimited'],
        top: 20,
      },
      grid: {
        left: 100,
      },
      xAxis: {
        type: 'value',
        name: 'Users',
        axisLabel: {
          formatter: '{value}',
        },
      },
      yAxis: {
        type: 'category',
        inverse: true,
        data: ['Income'],
        axisLabel: {
          margin: 20,
          rich: {
            value: {
              lineHeight: 30,
              align: 'center',
            },
            Sunny: {
              height: 40,
              align: 'center',
              backgroundColor: {},
            },
          },
        },
      },
      series: [
        {
          name: 'Free Trail',
          type: 'bar',
          data: [19],
          label: seriesLabel,
        },
        {
          name: 'Standard',
          type: 'bar',
          label: seriesLabel,
          data: [2],
        },
        {
          name: 'Unlimited',
          type: 'bar',
          label: seriesLabel,
          data: [1],
        },
      ],
    };

    const options: any = {
      title: {
        text: 'Videos per category',
        left: 'center',
        top: 20,
        textStyle: {
          color: '#000',
        },
      },
      tooltip: {
        trigger: 'item',
      },
      legend: {
        top: '90%',
        left: 'center',
      },
      visualMap: {
        show: false,
        min: 0,
        max: 10,
        inRange: {
          colorLightness: [0, 1],
        },
      },
      series: [
        {
          name: 'Generated Videos',
          type: 'pie',
          radius: '55%',
          center: ['50%', '50%'],
          data: [
            { value: 5, name: 'Youtube Ads' },
            { value: 3, name: 'Instagram' },
            { value: 1, name: 'TikTok' },
            { value: 1, name: 'TV Ads' },
            { value: 4, name: 'Facebook' },
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
            return Math.random() * 5000;
          },
        },
      ],
    };

    let options2: any = {
      title: {
        text: 'Users Gender',
        left: 30,
        top: 23,
        textStyle: {
          color: '#000',
        },
      },
      tooltip: {
        trigger: 'item',
      },
      legend: {
        top: '5%',
        left: 'center',
      },
      series: [
        {
          name: 'Users Gender',
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
            { value: 9, name: 'Male' },
            { value: 13, name: 'Female' },
          ],
        },
      ],
    };

    const options3: any = {
      title: {
        text: 'Videos per day/last week',
        left: 'center',
        top: 20,
        textStyle: {
          color: '#000',
        },
      },
      xAxis: {
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      },
      yAxis: {
        type: 'value',
      },
      series: [
        {
          data: [1, 2, 1, 5, 0, 3, 2],
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
    chart4.setOption(option4);
  }
}

import { Chart } from "react-chartjs-2";
import {
  Chart as ChartJS,
  LineElement,
  PointElement,
  LinearScale,
  RadialLinearScale,
} from "chart.js";

export default function Radar({ data }) {
  ChartJS.register(LineElement, PointElement, LinearScale, RadialLinearScale);
  return <Chart type="radar" data={data}></Chart>;
}

import { Chart } from "react-chartjs-2";
import {
  Chart as ChartJS,
  LineElement,
  PointElement,
  LinearScale,
  CategoryScale,
  Legend,
  Title,
} from "chart.js";

export default function Line({ data, options }) {
  ChartJS.register(
    LineElement,
    PointElement,
    LinearScale,
    CategoryScale,
    Legend,
    Title
  );

  return <Chart type="line" data={data} options={options}></Chart>;
}

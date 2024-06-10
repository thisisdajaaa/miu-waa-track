import { FC } from "react";
import Layout from "./components/Layout";
import Dashboard from "./pages/Dashboard";

const App: FC = () => {
  return (
    <Layout>
      <Dashboard />
    </Layout>
  );
};

export default App;

import { useState } from "react";

function App() {
  const [count, setCount] = useState<number>(0);

  return (
    <div className="flex min-h-screen flex-col">
      <main className="flex-1 overflow-x-auto">
        <div className="mx-auto my-10 max-w-4xl overflow-hidden">
          <div className="flex flex-col items-center">
            <p className="block">{count}</p>
            <button onClick={() => setCount((prev) => prev + 1)}>Click</button>
          </div>
        </div>
      </main>
    </div>
  );
}

export default App;

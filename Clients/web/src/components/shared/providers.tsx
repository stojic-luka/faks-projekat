import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { AuthProvider } from "../../contexts/useAuth";

const Providers = ({ children }: { children: React.ReactNode }) => {
  const queryClient = new QueryClient({
    defaultOptions: {
      queries: {
        refetchOnMount: false,
        refetchOnWindowFocus: false,
        refetchOnReconnect: false,
        retry: 0,
        staleTime: 1 * 1000,
      },
    },
  });

  return (
    <QueryClientProvider client={queryClient}>
      <AuthProvider>
        <>{children}</>
      </AuthProvider>
    </QueryClientProvider>
  );
};

export default Providers;

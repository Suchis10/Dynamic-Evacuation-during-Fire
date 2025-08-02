import java.util.*;

class dijkstras{
    static class Edge{
        int src;
        int dest;

        public Edge(int s, int d){
            this.src = s;
            this.dest = d;
        }
    }
    public static void creategraph(ArrayList<Edge>[] graph){
       for(int i =0; i<graph.length; i++){
        graph[i] = new ArrayList<>();
       }

       graph[9].add(new Edge(9, 5));
       graph[9].add(new Edge(9, 1));
       
       graph[5].add(new Edge(5, 2));
       //graph[5].add(new Edge(5, 1));
       graph[5].add(new Edge(5, 9));

       graph[2].add(new Edge(2, 5));
       graph[2].add(new Edge(2, 4));

       graph[4].add(new Edge(4, 2));

       graph[1].add(new Edge(1, 9));
       //graph[1].add(new Edge(1, 5));
    }
    // implementing the Priority Queue
    static class Pair implements Comparable<Pair>{
       int n;
       int path;

       public Pair(int n, int path){
        this.n = n;
        this.path = path;
       }

       @Override
       public int compareTo(Pair p2){
        return this.path - p2.path;
       }
    }
    public static void dijktrasAlgo(ArrayList<Edge>[] graph , int src){
        int dis[] = new int[graph.length];
        PriorityQueue<Pair> pq = new PriorityQueue<>();

        for(int i=0; i<graph.length; i++){
            if(i != src){
                dis[i] = Integer.MAX_VALUE;
            }
        }

        boolean visit[] = new boolean[graph.length];
        pq.add(new Pair(src, 0));

        while(!pq.isEmpty()){
             Pair curr = pq.remove();
             if(visit[curr.n] == false){
                visit[curr.n] = true;
                for(int i = 0; i <graph[curr.n].size(); i++){
                    Edge e = graph[curr.n].get(i);
                    int u = e.src;
                    int v = e.dest;
                    int wt = e.wt;

                    if(dis[u] + wt < dis[v]){
                        dis[v] = dis[u] + wt;
                        pq.add(new Pair(v, dis[v]));
                    }
                }
             }
        }
        for(int i=0; i<dis.length; i++){
            System.out.print(dis[i] + " ");
        }
    }
    public static void calculateindeg(ArrayList<Edge>[] graph, int indeg[]){
        for(int i=0; i<graph.length; i++){
            for(int j=0; j<graph[i].size(); j++){
                Edge e = graph[i].get(j);
                indeg[e.dest]++;
            }
        }
    }
    public static boolean detectcycle(ArrayList<Edge>[] graph, int V){
        int indeg[] = new int[graph.length];
        calculateindeg(graph, indeg);
        Queue<Integer> q = new LinkedList<>();

        for(int i=0; i<graph.length; i++){
            if(indeg[i] == 0){
                q.add(i);
            }
        }
        int count =0;
        // BFS traversal
        while(!q.isEmpty()){
            int curr = q.remove();
            count++;

            for(int i=0; i<graph[curr].size(); i++){
                Edge e = graph[curr].get(i);
                indeg[e.dest]--;
                if(indeg[e.dest] == 0){
                    q.add(e.dest);
                }
            }
        }
        if(count != V){
            return true;
        }
        else{
            return false;
        }
    }
    static class Pair{
        int first;
        int second;

        public Pair(int first, int second){
            this.first = first;
            this.second = second;
        }
    }
    public static boolean detectcycleBFS(ArrayList<Edge>[] graph){
        boolean visit[] = new boolean[graph.length];
        for(int i=0; i<graph.length; i++){
            if(!visit[i]){
                if(detectcycleBFSutil(graph, i, -1, visit)){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean detectcycleBFSutil(ArrayList<Edge>[] graph, int curr, int parent, boolean visit[]){
        visit[curr] = true;
        Queue<Integer> q = new LinkedList<>();
        q.add(curr);

        while(!q.isEmpty()){
            int c = q.peek().first;
            int p = q.peek().second;
            q.remove();
            for(int i=0; i<graph[c].size(); i++){
                Edge e = graph[c].get(i);
                // Case 3 --> when neighbour is not visited then visit it
                if(!visit[e.dest]){
                    visit[e.dest] = true;
                    q.add();
                }
                // case 1--> 
                else if(e.dest != parent){
                    return true;
                }
            }
        }
        return false;
    }
    public static void main(String[] args){
        int V = 10;
        ArrayList<Edge>[] graph = new ArrayList[V];
        creategraph(graph);
        System.out.println(detectcycleBFS(graph));
    }
}
package dev.okano.camunda.techchallenge;

import java.util.*;

public class DFS {
    public List<String> findPath(Map<String, Set<String>> graph, String startNode, String endNode) {
        List<String> path = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        if (dfs(graph, startNode, endNode, visited, path)) {
            return path;
        }

        return Collections.emptyList();
    }

    private boolean dfs(Map<String, Set<String>> graph, String current, String end, Set<String> visited, List<String> path) {
        path.add(current);
        visited.add(current);

        if (Objects.equals(current, end)) {
            return true;
        }

        for (String neighbour : graph.getOrDefault(current, Collections.emptySet())) {
            if (!visited.contains(neighbour)) {
                if (dfs(graph, neighbour, end, visited, path)) {
                    return true;
                }
            }
        }

        path.removeLast();
        return false;
    }
}

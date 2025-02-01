package dev.okano.camunda.techchallenge;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.xml.ModelInstance;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    private final BPMNFetcher fetcher = new BPMNFetcher();
    private final Map<String, Set<String>> graph = new HashMap<>();
    private final DFS dfs = new DFS();

    private void run(String startId, String endId) {
        var bpmnFetchResponse = fetcher.fetch("https://n35ro2ic4d.execute-api.eu-central-1.amazonaws.com/prod/engine-rest/process-definition/key/invoice/xml");

        var modelInstance = Bpmn.readModelFromStream(bpmnFetchResponse.bpmnAsStream());
        populateGraph(modelInstance);

        var path = dfs.findPath(graph, startId, endId);

        if (path.isEmpty()) {
            System.exit(-1);
        }

        System.out.printf("The path from %s to %s is:\n", startId, endId);
        System.out.println(path);
    }

    // Just to extract the graph build step from the logic.
    // Since it's modifying external data isn't the best approach
    // but it's just to keep the main execution code "clearer"
    private void populateGraph(ModelInstance modelInstance) {
        var nodes = modelInstance.getModelElementsByType(FlowNode.class);

        // Populate the graph definition
        for (FlowNode node : nodes) {
            graph.putIfAbsent(node.getId(), new HashSet<>());

            // populate the edges
            for (SequenceFlow flow : node.getOutgoing()) {
                graph.get(node.getId()).add(flow.getTarget().getId());
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.exit(-1);
        }

        new Main().run(args[0], args[1]);
//        new Main().run("approveInvoice", "invoiceProcessed");
    }
}

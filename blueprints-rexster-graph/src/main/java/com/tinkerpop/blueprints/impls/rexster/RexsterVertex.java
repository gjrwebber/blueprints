package com.tinkerpop.blueprints.impls.rexster;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Query;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.DefaultQuery;
import com.tinkerpop.blueprints.util.MultiIterable;
import com.tinkerpop.blueprints.util.StringFactory;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class RexsterVertex extends RexsterElement implements Vertex {

    public RexsterVertex(final JSONObject rawVertex, final RexsterGraph graph) {
        super(rawVertex, graph);
    }


    public Iterable<Edge> getOutEdges(final String... labels) {
        if (labels.length == 0)
            return new RexsterEdgeIterable(this.graph.getGraphURI() + RexsterTokens.SLASH_VERTICES_SLASH + RestHelper.encode(this.getId()) + RexsterTokens.SLASH_OUTE, this.graph);

        else if (labels.length == 1) {
            return new RexsterEdgeIterable(this.graph.getGraphURI() + RexsterTokens.SLASH_VERTICES_SLASH + RestHelper.encode(this.getId()) + RexsterTokens.SLASH_OUTE + RexsterTokens.QUESTION + RexsterTokens._LABEL_EQUALS + RestHelper.encode(labels[0]), this.graph);
        } else {
            final List<Iterable<Edge>> edges = new ArrayList<Iterable<Edge>>();
            for (final Object filter : labels) {
                edges.add(new RexsterEdgeIterable(this.graph.getGraphURI() + RexsterTokens.SLASH_VERTICES_SLASH + RestHelper.encode(this.getId()) + RexsterTokens.SLASH_OUTE + RexsterTokens.QUESTION + RexsterTokens._LABEL_EQUALS + RestHelper.encode(filter), this.graph));
            }
            return new MultiIterable<Edge>(edges);
        }
    }

    public Iterable<Edge> getInEdges(final String... labels) {
        if (labels.length == 0)
            return new RexsterEdgeIterable(this.graph.getGraphURI() + RexsterTokens.SLASH_VERTICES_SLASH + RestHelper.encode(this.getId()) + RexsterTokens.SLASH_INE, this.graph);

        else if (labels.length == 1) {
            return new RexsterEdgeIterable(this.graph.getGraphURI() + RexsterTokens.SLASH_VERTICES_SLASH + RestHelper.encode(this.getId()) + RexsterTokens.SLASH_INE + RexsterTokens.QUESTION + RexsterTokens._LABEL_EQUALS + RestHelper.encode(labels[0]), this.graph);
        } else {

            final List<Iterable<Edge>> edges = new ArrayList<Iterable<Edge>>();
            for (final Object filter : labels) {
                edges.add(new RexsterEdgeIterable(this.graph.getGraphURI() + RexsterTokens.SLASH_VERTICES_SLASH + RestHelper.encode(this.getId()) + RexsterTokens.SLASH_INE + RexsterTokens.QUESTION + RexsterTokens._LABEL_EQUALS + RestHelper.encode(filter), this.graph));
            }
            return new MultiIterable<Edge>(edges);
        }
    }

    public Query query() {
        return new DefaultQuery(this);
    }

    public String toString() {
        return StringFactory.vertexString(this);
    }

    public JSONObject getRawVertex() {
        return RestHelper.getResultObject(graph.getGraphURI() + RexsterTokens.SLASH_VERTICES_SLASH + this.getId());
    }

}
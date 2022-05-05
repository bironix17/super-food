package ru.bironix.super_food.config.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.zalando.logbook.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CustomHttpLogFormatter implements HttpLogFormatter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String format(final Precorrelation precorrelation, final HttpRequest request) throws IOException {
        final String body = new String(request.getBody());

        final StringBuilder result = new StringBuilder(body.length() + 2048);

        result.append("<<<<<<<<<<<<<<<<<<<<<<<<<<<<REQUEST<<<<<<<<<<<<<<<<<<<<<<<<<<< ");
        result.append('\n');

        result.append("Remote: ");
        result.append(request.getRemote());
        result.append('\n');

        result.append(request.getMethod());
        result.append(' ');
        result.append(request.getProtocolVersion());
        result.append('\n');

        writeHeaders(request.getHeaders(), result);
        result.append('\n');
        if (isValidJSON(body)) writeBodyJson(body, result);
        else writeBodyPlain(body, result);

        result.append("<<<<<<<<<<<<<<<<<<<<<<<<<<<<REQUEST END<<<<<<<<<<<<<<<<<<<<<<<<<<< ");

        return result.toString();
    }

    @Override
    public String format(final Correlation correlation, final HttpResponse response) throws IOException {
        final String body = new String(response.getBody());

        final StringBuilder result = new StringBuilder(body.length() + 2048);

        result.append(">>>>>>>>>>>>>>>>>>>>>>>>>>RESPONSE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
        result.append("\nDuration: ");
        result.append(correlation.getDuration().toMillis());
        result.append(" ms\n");

        result.append(response.getProtocolVersion());
        result.append(' ');
        result.append(response.getStatus());
        final String reasonPhrase = response.getReasonPhrase();
        if (reasonPhrase != null) {
            result.append(' ');
            result.append(reasonPhrase);
        }

        result.append('\n');

        writeHeaders(response.getHeaders(), result);

        if (isValidJSON(body)) writeBodyJson(body, result);
        else writeBodyPlain(body, result);

        result.append('\n');
        result.append(">>>>>>>>>>>>>>>>>>>>>>>>>>RESPONSE END>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
        return result.toString();
    }


    private void writeHeaders(final Map<String, List<String>> headers, final StringBuilder output) {
        if (headers.isEmpty()) {
            return;
        }

        for (final Map.Entry<String, List<String>> entry : headers.entrySet()) {
            output.append(entry.getKey());
            output.append(": ");
            final List<String> headerValues = entry.getValue();
            if (!headerValues.isEmpty()) {
                for (final String value : entry.getValue()) {
                    output.append(value);
                    output.append(", ");
                }
                output.setLength(output.length() - 2); // discard last comma
            }
            output.append('\n');
        }
    }

    private void writeBodyPlain(final String body, final StringBuilder output) {
        if (!body.isEmpty()) {
            output.append('\n');
            output.append(body);
        } else {
            output.setLength(output.length() - 1); // discard last newline
        }
    }

    private void writeBodyJson(final String body, final StringBuilder output) {
        if (!body.isEmpty()) {
            output.append('\n');
            try {
                Object object = objectMapper.readTree(body);
                output.append(
                        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object)
                );
            } catch (JsonProcessingException ignored) {
            }
        } else {
            output.setLength(output.length() - 1); // discard last newline
        }
    }

    public boolean isValidJSON(final String json) {
        try {
            objectMapper.readTree(json);

        } catch (JsonProcessingException jpe) {
            return false;
        }

        return true;
    }
}

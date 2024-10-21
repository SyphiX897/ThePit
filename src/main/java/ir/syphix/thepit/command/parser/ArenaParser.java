package ir.syphix.thepit.command.parser;

import ir.syphix.thepit.core.arena.Arena;
import ir.syphix.thepit.core.arena.ArenaManager;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.caption.Caption;
import org.incendo.cloud.caption.CaptionVariable;
import org.incendo.cloud.caption.StandardCaptionKeys;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.exception.parsing.ParserException;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.ParserDescriptor;
import org.incendo.cloud.suggestion.BlockingSuggestionProvider;
import org.incendo.cloud.suggestion.Suggestion;

public class ArenaParser<C> implements ArgumentParser<C, Arena>, BlockingSuggestionProvider<C> {
    @Override
    public @NonNull ArgumentParseResult<Arena> parse(@NonNull CommandContext<@NonNull C> commandContext, @NonNull CommandInput commandInput) {
        String input = commandInput.readString();
        Arena arena = ArenaManager.arena(input);

        if (arena == null) {
            return ArgumentParseResult.failure(new ArenaParserException(input, commandContext));
        }

        return ArgumentParseResult.success(arena);
    }

    @Override
    public @NonNull Iterable<? extends @NonNull Suggestion> suggestions(@NonNull CommandContext<C> context, @NonNull CommandInput input) {
        String inputString = input.readString();
        return ArenaManager.arenas().stream().map(Arena::id).filter(arena -> arena.startsWith(inputString)).map(Suggestion::suggestion).toList();
    }


    public static final class ArenaParserException extends ParserException {
        public ArenaParserException(String input, CommandContext<?> context) {
            super(Arena.class, context, StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_STRING, CaptionVariable.of("input", input));
        }

    }

    public static <C> ParserDescriptor<C, Arena> arenaParser() {
        return ParserDescriptor.of(new ArenaParser<>(), Arena.class);
    }
}

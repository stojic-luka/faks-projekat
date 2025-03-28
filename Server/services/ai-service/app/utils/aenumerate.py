from typing import AsyncGenerator, AsyncIterable, Tuple, TypeVar


T = TypeVar("T")


async def aenumerate(
    async_iterable: AsyncIterable[T], start: int = 0
) -> AsyncGenerator[Tuple[int, T], None]:
    idx = start
    async for item in async_iterable:
        yield idx, item
        idx += 1

import { useCallback } from "react";
import { ChatMessage } from "../../types/chatTypes/chatModels";
import { useIndexedDB } from "../generic";
import { IndexedDBConfig } from "../generic/useIndexedDB";

const config: IndexedDBConfig = {
  dbName: "Chat",
  version: 1,
  storeName: "Messages",
  keyPath: "id",
  indexes: [
    {
      indexName: "key_user",
      keyPath: ["user_id"],
      options: { unique: false },
    },
  ],
};

export const useChatIndexedDB = () => {
  const { addItem, getItem, getAllItems, updateItem, deleteItem, deleteAllItems } = useIndexedDB<ChatMessage>(config);

  const addMessage = useCallback((message: ChatMessage) => addItem(message), [addItem]);

  const getMessage = useCallback((id: string) => getItem(id), [getItem]);
  const getAllMessages = useCallback(
    async (userId: string) => {
      const messages = await getAllItems("key_user", IDBKeyRange.only([userId]));
      if (messages) messages.sort((a, b) => a.timestamp - b.timestamp);
      return messages;
    },
    [getAllItems]
  );

  const updateMessage = useCallback(
    (message: ChatMessage, prevId?: string) => {
      if (prevId && prevId !== message.id) {
        return new Promise((resolve, reject) => {
          deleteItem(prevId)
            .then(() => addMessage(message))
            .then(resolve)
            .catch(reject);
        });
      } else {
        return updateItem(message);
      }
    },
    [updateItem, deleteItem, addMessage]
  );

  const deleteMessage = useCallback((id: string) => deleteItem(id), [deleteItem]);
  const deleteAllMessages = useCallback((userId: string) => deleteAllItems("key_user", IDBKeyRange.only([userId])), [deleteAllItems]);

  return {
    addMessage,
    getMessage,
    getAllMessages,
    updateMessage,
    deleteMessage,
    deleteAllMessages,
  };
};

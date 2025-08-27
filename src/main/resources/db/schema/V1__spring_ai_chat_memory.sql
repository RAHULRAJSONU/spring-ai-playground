-- V1__spring_ai_chat_memory.sql
CREATE TABLE IF NOT EXISTS spring_ai_chat_memory (
  id            BIGSERIAL PRIMARY KEY,
  conversation_id TEXT NOT NULL,
  message_index   INT  NOT NULL,               -- stable ordering within a convo write
  role            VARCHAR(20) NOT NULL,        -- user|assistant|system|tool
  content         TEXT NOT NULL,               -- message text
  metadata        JSONB NULL,                  -- model/tool info, external refs
  created_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- lookups & range scans by conversation
CREATE INDEX IF NOT EXISTS idx_chat_memory_convo_created
  ON spring_ai_chat_memory (conversation_id, created_at);

-- optional for strict ordering (if you ever batch insert and then page)
CREATE UNIQUE INDEX IF NOT EXISTS ux_chat_memory_convo_idx
  ON spring_ai_chat_memory (conversation_id, message_index);

-- housekeeping: drop messages older than N days (if you adopt TTL)
-- (schedule as a job; see Ops section)

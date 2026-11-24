-- Run before starting the application against the legacy smallint schema.
-- The legacy enum in 5e7556e used MALE, FEMALE, TRANS in that order.
-- The current enum has no TRANS value. Resolve any such rows separately.
BEGIN;

LOCK TABLE public.guest IN ACCESS EXCLUSIVE MODE;

DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM public.guest WHERE gender NOT IN (0, 1)) THEN
        RAISE EXCEPTION 'Guest gender migration stopped: found an ordinal without a verified mapping';
    END IF;
END
$$;

ALTER TABLE public.guest DROP CONSTRAINT guest_gender_check;

ALTER TABLE public.guest
    ALTER COLUMN gender TYPE varchar(255)
    USING (CASE gender WHEN 0 THEN 'MALE' WHEN 1 THEN 'FEMALE' END);

ALTER TABLE public.guest
    ADD CONSTRAINT guest_gender_check CHECK (gender IN ('MALE', 'FEMALE', 'OTHER'));

COMMIT;
